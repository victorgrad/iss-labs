package model;

import javax.persistence.*;


@javax.persistence.Entity
@Table(name = "users")
public class User extends Entity<String>{
    private String firstName;
    private String lastName;
    private Status status;
    private String passwordHash;


    public User(){}
    /**
     * Class constructor
     * @param id string the id of the user
     * @param firstName string the firstname of the user
     * @param lastName string the lastname of the user
     */
    public User(String id,Status status, String firstName, String lastName) {
        super.setId(id);
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = null;
    }

    @Id
    public String getId(){return super.getId();}

    public void setId(String username){
        super.setId(username);
    }

    /**
     * Returns the hash of the password
     * @return string
     */
    @Column(name = "password")
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the hash of the password
     * @param passwordHash , String the password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Returns the firstname of the user
     * @return String
     */
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the lastname of the user
     * @return String
     */
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    @Column(name = "status")
    @Convert(converter = StatusAttributeConverter.class)
    public Status getStatus() {
        return status;
    }

    /**
     * Returns a string with information about the user
     * @return String
     */
    @Override
    public String toString() {
        return "User{" + " username:'" + getId() + "', firstName: '" + firstName + "', lastName: '" + lastName + "' }";
    }

    /**
     * Defines the equality between two User objects
     * @param o the object to which is compared
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User that)) {
            return false;
        }
        return getId().equals(that.getId());
    }

}
