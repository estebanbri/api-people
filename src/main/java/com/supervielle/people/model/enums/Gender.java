package com.supervielle.people.model.enums;

public enum Gender {
    MALE {
        @Override
        public String toString() {
            return "MALE";
        }
    },
    FEMALE {
        @Override
        public String toString() {
            return "FEMALE";
        }
    }
}
