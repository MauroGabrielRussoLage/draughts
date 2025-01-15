package ec.com.sofka.gateway.dto;

public class BoardDTO {
    private String[][] boxes;

    public BoardDTO(String[][] boxes) {
        this.boxes = boxes;
    }

    public BoardDTO() {
    }

    public String[][] getBoxes() {
        return boxes;
    }

    public void setBoxes(String[][] boxes) {
        this.boxes = boxes;
    }
}
