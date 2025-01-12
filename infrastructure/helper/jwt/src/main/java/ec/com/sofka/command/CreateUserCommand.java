package ec.com.sofka.command;

import ec.com.sofka.model.User;

import java.util.List;

public class CreateUserCommand {
    private String name;
    private String lastname;
    private String username;
    private String password;
    private List<String> roles;

    public CreateUserCommand(String name, String lastname, String username, String password, List<String> roles) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public CreateUserCommand() {
    }

    private CreateUserCommand(Builder builder) {
        this.name = builder.name;
        this.lastname = builder.lastname;
        this.username = builder.username;
        this.password = builder.password;
        this.roles = builder.roles;
    }

    public User toUser() {
        return new User(this.username, this.password, this.roles);
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "CreateUserCommand{" +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }

    public static class Builder {
        private String name;
        private String lastname;
        private String username;
        private String password;
        private List<String> roles;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public CreateUserCommand build() {
            return new CreateUserCommand(this);
        }
    }
}
