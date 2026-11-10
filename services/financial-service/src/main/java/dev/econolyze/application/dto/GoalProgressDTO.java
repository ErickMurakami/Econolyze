package dev.econolyze.application.dto;

public class GoalProgressDTO {
    private Long id;
    private String name;
    private Double progress;

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Double getProgress() {
        return this.progress;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setProgress(final Double progress) {
        this.progress = progress;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GoalProgressDTO)) return false;
        final GoalProgressDTO other = (GoalProgressDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$progress = this.getProgress();
        final Object other$progress = other.getProgress();
        if (this$progress == null ? other$progress != null : !this$progress.equals(other$progress)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GoalProgressDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $progress = this.getProgress();
        result = result * PRIME + ($progress == null ? 43 : $progress.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "GoalProgressDTO(id=" + this.getId() + ", name=" + this.getName() + ", progress=" + this.getProgress() + ")";
    }

    public GoalProgressDTO(final Long id, final String name, final Double progress) {
        this.id = id;
        this.name = name;
        this.progress = progress;
    }

    public GoalProgressDTO() {
    }
}
