
package entities;

import java.time.LocalDate;
import java.util.Objects;

public class Assignment {
    private int as_id;
    private String title;
    private String description;
    private LocalDate subDateTime;

    public Assignment() {
    }

    public Assignment(String title, String description, LocalDate subDateTime) {
        this.title = title;
        this.description = description;
        this.subDateTime = subDateTime;
    }

    public Assignment(int as_id, String title, String description, LocalDate subDateTime) {
        this.as_id = as_id;
        this.title = title;
        this.description = description;
        this.subDateTime = subDateTime;
    }

    public int getAs_id() {
        return as_id;
    }

    public void setAs_id(int as_id) {
        this.as_id = as_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getSubDateTime() {
        return subDateTime;
    }

    public void setSubDateTime(LocalDate subDateTime) {
        this.subDateTime = subDateTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.as_id;
        hash = 17 * hash + Objects.hashCode(this.title);
        hash = 17 * hash + Objects.hashCode(this.description);
        hash = 17 * hash + Objects.hashCode(this.subDateTime);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Assignment other = (Assignment) obj;
        if (this.as_id != other.as_id) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.subDateTime, other.subDateTime)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Assignment{" + "as_id=" + as_id + ", title=" + title + ", description=" + description + ", subDateTime=" + subDateTime + '}';
    }
    
    
}
