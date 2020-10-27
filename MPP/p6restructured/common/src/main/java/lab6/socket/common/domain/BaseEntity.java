package lab6.socket.common.domain;

import java.io.Serializable;

public class BaseEntity<ID> implements Serializable {
    public ID id;

    public BaseEntity(ID _id) { this.id = _id; }
    public ID getId() { return id; }
    public void setId(ID _id) { this.id = _id; }

    @Override
    public String toString() { return "BaseEntity{ id = " + id + " }"; }
}
