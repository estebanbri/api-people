package com.supervielle.people.service.familytree;

import com.supervielle.people.model.Person;
import com.supervielle.people.repository.PeopleCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FamilyTree {

    @Autowired
    private PeopleCrudRepository repository;

    //Class node representing a name and its parent
    private class Node{
        public Long id;
        public Long parent;
        public Node(Long id, Long parent){
            this.id = id;
            this.parent = parent;
        }
    }

    //Generation class that holds a list of nodes for each generation.
    private class Generation{
        public ArrayList<Node> nodes;

        public Generation(){
            nodes =  new ArrayList<Node>();
        }

        public Generation(Long id, Long parent){
            nodes =  new ArrayList<Node>();
            nodes.add(0, new Node(id, parent));
        }

        public Generation(ArrayList<Node> children){
            nodes = new ArrayList<Node>();
            nodes.addAll(children);
        }
        public void addNode(Long id, Long parent){
            nodes.add(new Node(id, parent));
        }

        public boolean areChildren(ArrayList<Node> parent){ //checks whether the current generation are the children of the previous generation in the tree
            for(Node child : nodes){
                for(Node par : parent) {
                    if(child.parent.equals(par.id)){
                        return true;
                    }
                }
            }
            return false;
        }
    }

    //Priority queue class that holds any nodes whose parents/children are not yet in the family tree
    private class Queue{
        private ArrayList<Node> queue;
        private ArrayList<Long> parents;

        public Queue(){
            queue = new ArrayList<Node>();
            parents = new ArrayList<Long>();
        }

        public void enqueue(Long id, Long parent){ //adds new node into the priority queue
            queue.add(new Node(id, parent));
            parents.add(parent);
        }

        public boolean containsParent(Long parent){
            if(parents.contains(parent)) return true;
            else return false;
        }

        public ArrayList<Node> dequeue(Long parent){ //removes node(s) whose parent matches the input string and adds it to the family tree
            ArrayList<Node> children = new ArrayList<Node>();
            for(Node node : this.queue){
                if(node.parent.equals(parent)){
                    children.add(node);
                }
            }
            parents.remove(parent);
            return children;
        }

        public boolean empty(){
            if(queue.size() > 0) return false;
            else return true;
        }

    }

    private ArrayList<Generation> genTree;  //overall tree that holds the list of generations
    private Queue queue;	//queue which temporarily holds nodes which have not yet been added to the overall family tree

    public FamilyTree() {
        genTree = new ArrayList<Generation>();
        queue = new Queue();
    }

    private void addChildren(int i, Long parent){ //adds children nodes whose parent matches the input string
        if(genTree.size() > i+1 && genTree.get(i+1).areChildren(genTree.get(i).nodes)) {
            genTree.get(i+1).nodes.addAll(queue.dequeue(parent));
        } else if (genTree.size() > i+1 && !genTree.get(i+1).areChildren(genTree.get(i).nodes)){
            Generation gen = new Generation(queue.dequeue(parent));
            genTree.add(i+1, gen);
        } else {
            Generation gen = new Generation(queue.dequeue(parent));
            genTree.add(gen);
        }
    }


    public void fillTree(){

        List<Person> personList = repository.findAll();

        if(personList.isEmpty()) return;


        for(Person person : personList){
            boolean found = false;
            if(genTree.isEmpty() || (person.getParentId() == null)){
                Generation gen = new Generation(person.getId(), person.getParentId());
                genTree.add(0, gen);
                found = true;
            } else {
                for(int i = 0; !found && i < genTree.size(); i++){
                    for(Node node : genTree.get(i).nodes){  //check if the read node is a parent/child of any node in the tree (check generation after generation)
                        if(person.getParentId().equals(node.parent) && i == 0){ //check if it is a parent
                            Generation gen = new Generation(person.getId(), person.getParentId());
                            genTree.add(0, gen);
                            found = true;
                            break;
                        } else if (person.getId().equals(node.parent) && i > 0 && genTree.get(i).areChildren(genTree.get(i-1).nodes)) {
                            genTree.get(i-1).addNode(person.getId(), person.getParentId());
                            found = true;
                            break;
                        } else if (person.getId().equals(node.parent) && i > 0 && !genTree.get(i).areChildren(genTree.get(i-1).nodes)) {
                            Generation gen = new Generation(person.getId(), person.getParentId());
                            genTree.add(i-1, gen);
                            found = true;
                            break;
                        } else if (person.getId().equals(node.id) && i+1 == genTree.size()){  //now check if it is a child of any node
                            Generation gen = new Generation(person.getId(), person.getParentId());
                            genTree.add(gen);
                            found = true;
                            break;
                        }	else if (person.getId().equals(node.id) && i+1 < genTree.size() && genTree.get(i+1).areChildren(genTree.get(i).nodes)) {
                            genTree.get(i+1).addNode(person.getId(), person.getParentId());
                            found = true;
                            break;
                        } else if (person.getId().equals(node.id) && i+1 < genTree.size() && genTree.get(i+1).areChildren(genTree.get(i).nodes)) {
                            Generation gen = new Generation(person.getId(), person.getParentId());
                            genTree.add(i-1, gen);
                            found = true;
                            break;
                        }
                    }
                }
            }
            if(!found){
                queue.enqueue(person.getParentId(), person.getId());
            }
        }

        //This part of the function searches through every node in the pre-filled tree and checks whether it has any children stored in the p.queue
        for(int i = 0; i < genTree.size() && !queue.empty(); i++){
            for(Node node : genTree.get(i).nodes){
                if(queue.containsParent(node.id)){
                    this.addChildren(i, node.id);
                }
                if(queue.empty()) break;
            }
        }
    }

    public String search(Long source, Long target){
        String result = "NO RELATIONSHIP";
        int levelFound = 0;
        for(int i = 0; i < genTree.size(); i++){
            ArrayList<Node> nodes = genTree.get(0).nodes;
            Optional<Node> sourceNode = nodes.stream().filter(n -> n.id.equals(source)).findFirst();
            Optional<Node> targetNode = nodes.stream().filter(n -> n.id.equals(target)).findFirst();

            if (sourceNode.isPresent() && targetNode.isPresent()) {
                if(sourceNode.get().parent.equals(targetNode.get().parent)){
                    result = "Brothers"; break;
                }
                result = "Cousins"; break;
            }
            if(sourceNode.isPresent()) levelFound = i;
            if(targetNode.isPresent() && i == levelFound - 1 ) { result = "Uncle"; break; }
            i--;
        }
        return result;
    }

}
