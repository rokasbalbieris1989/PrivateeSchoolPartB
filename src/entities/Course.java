
package entities;

import java.time.LocalDate;
import java.util.Objects;

public class Course {
    private int cr_id;
    private String title;
    private String stream;
    private String type;
    private LocalDate start_date;
    private LocalDate end_date;

    public Course() {
    }

    public Course(int cr_id,String title, String stream, String type, LocalDate start_date, LocalDate end_date) {
        this.cr_id=cr_id;
        this.title = title;
        this.stream = stream;
        this.type = type;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Course(String title, String stream, String type, LocalDate start_date, LocalDate end_date) {
        this.title = title;
        this.stream = stream;
        this.type = type;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getCr_id() {
        return cr_id;
    }

    public void setCr_id(int cr_id) {
        this.cr_id = cr_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.title);
        hash = 71 * hash + Objects.hashCode(this.stream);
        hash = 71 * hash + Objects.hashCode(this.type);
        hash = 71 * hash + Objects.hashCode(this.start_date);
        hash = 71 * hash + Objects.hashCode(this.end_date);
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
        final Course other = (Course) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.stream, other.stream)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.start_date, other.start_date)) {
            return false;
        }
        if (!Objects.equals(this.end_date, other.end_date)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Course{" + "title=" + title + ", stream=" + stream + ", type=" + type + ", start_date=" + start_date + ", end_date=" + end_date + '}';
    }
    
    
}
