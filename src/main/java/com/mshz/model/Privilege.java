package com.mshz.model;

public class Privilege {
    private Long id;

    private Boolean constrained;

    private String authority;

    private String entity;

    private String action;

    public Privilege() {
    }

    public Privilege(Long id, Boolean constrained, String authority, String entity, String action) {
        this.id = id;
        this.constrained = constrained;
        this.authority = authority;
        this.entity = entity;
        this.action = action;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isConstrained() {
        return this.constrained;
    }

    public Boolean getConstrained() {
        return this.constrained;
    }

    public void setConstrained(Boolean constrained) {
        this.constrained = constrained;
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getEntity() {
        return this.entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    // prettier-ignore
    @Override
    public String toString() {
        return "{" +
            "id:" + getId() +
            ", constrained:'" + isConstrained() + "'" +
            ", authority:'" + getAuthority() + "'" +
            ", entity:'" + getEntity() + "'" +
            ", action:'" + getAction() + "'" +
            "}";
    }


}
