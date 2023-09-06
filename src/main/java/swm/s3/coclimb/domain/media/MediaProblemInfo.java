package swm.s3.coclimb.domain.media;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@Getter
public class MediaProblemInfo {
    @Column(name = "problem_clear_date")
    private LocalDate clearDate;
    @Column
    private String gymName;
    @Column(name = "problem_color")
    private String color;
    @Column(name = "problem_is_clear")
    private Boolean isClear;
    @Column(name = "problem_perceived_difficulty")
    private String perceivedDifficulty;
    @Column(name = "problem_type")
    private String type;

    @Builder
    public MediaProblemInfo(LocalDate clearDate, String gymName, String color, Boolean isClear, String perceivedDifficulty, String type) {
        this.clearDate = clearDate;
        this.gymName = gymName;
        this.color = color;
        this.isClear = isClear;
        this.perceivedDifficulty = perceivedDifficulty;
        this.type = type;
    }

    protected void remove() {
        this.clearDate = null;
        this.gymName = null;
        this.color = null;
        this.isClear = null;
        this.perceivedDifficulty = null;
        this.type = null;
    }
}
