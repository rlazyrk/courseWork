package ua.lviv.iot.algo.part1.courseWork.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public final class Ticket extends Item {
    @Pattern(regexp = "[A-Z]{2}[0-9]{4}[A-Z]{2}",
            message = "The car number must match the pattern AA2222AA ")
    private String carNumber;
    @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]",
            message = "dataTime must be in the format YYYY-MM-DD HH:MM:SS")
    private String arrivalTime;
    @Id
    private Integer id;
    private Integer durationInMinute;

    @JsonIgnore
    public String getHeaders() {
        return "id, carNumber, dataTime, durationInMinute";
    }

    public String toCSV() {
        return id + "," + carNumber + "," + arrivalTime + "," + durationInMinute;
    }
}
