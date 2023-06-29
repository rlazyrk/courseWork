package ua.lviv.iot.algo.part1.courseWork.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public final class ParkingSpot extends Item {
    @Id
    private Integer id;
    private boolean isEmpty;
    @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]",
            message = "dataTime must be in the format YYYY-MM-DD HH:MM:SS")
    private String departureTime;
    @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]",
            message = "dataTime must be in the format YYYY-MM-DD HH:MM:SS")
    private String arrivalTime;
    @Pattern(regexp = "[A-Z]{2}[0-9]{4}[A-Z]{2}",
            message = "The car number must match the pattern AA2222AA ")
    private String carNumber;
    private Integer usageTimeInMinute;

    @JsonIgnore
    public String getHeaders() {
        return "id, isEmpty, departureTime, arrivalTime, carNumber, usageTimeInMinute";
    }

    public String toCSV() {
        return id + "," + isEmpty + "," + departureTime + "," + arrivalTime + "," + carNumber + "," + usageTimeInMinute;
    }
}
