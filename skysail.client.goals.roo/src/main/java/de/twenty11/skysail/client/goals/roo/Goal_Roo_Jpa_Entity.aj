// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package de.twenty11.skysail.client.goals.roo;

import de.twenty11.skysail.client.goals.roo.Goal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Goal_Roo_Jpa_Entity {
    
    declare @type: Goal: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Goal.id;
    
    @Version
    @Column(name = "version")
    private Integer Goal.version;
    
    public Long Goal.getId() {
        return this.id;
    }
    
    public void Goal.setId(Long id) {
        this.id = id;
    }
    
    public Integer Goal.getVersion() {
        return this.version;
    }
    
    public void Goal.setVersion(Integer version) {
        this.version = version;
    }
    
}
