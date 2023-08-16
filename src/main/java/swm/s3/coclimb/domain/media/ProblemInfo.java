package swm.s3.coclimb.domain.media;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class ProblemInfo {
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
    public ProblemInfo(String gymName, String color, Boolean isClear, String perceivedDifficulty, String type) {
        this.gymName = gymName;
        this.color = color;
        this.isClear = isClear;
        this.perceivedDifficulty = perceivedDifficulty;
        this.type = type;
    }
}
