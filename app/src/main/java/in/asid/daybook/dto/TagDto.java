package in.asid.daybook.dto;

import in.asid.daybook.models.Tag;

public class TagDto {

    private Integer id;
    private String name;

    public TagDto() {
    }

    public TagDto(Tag tag) {
        name = tag.getName();
        id = tag.getServerId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
