package com.revature.models;

public enum Role {



    BASIC_USER("Basic User"),
    TELLER("Teller"),
    LOCKED("Locked");


    private String roleName;


    Role(String name) {
        this.roleName = name;
    }

    /**
     * returns the name of the corresponding role in the database
     * @param name
     * @return
     */
    public static Role getByName(String name) {

        for (Role role : Role.values()) {
            if (role.roleName.equals(name)) {
                return role;
            }
        }

        return LOCKED;

    }

    @Override
    public String toString() {
        return roleName;
    }

}
