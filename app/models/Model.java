package models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import play.db.jpa.GenericModel;

@MappedSuperclass
public class Model extends GenericModel {

    @Id
    @Column(name="id")
    @GeneratedValue
    public Long mainId;

    public Long getId() {
        return mainId;
    }

    @Override
    public Object _key() {
        return getId();
    }

}
