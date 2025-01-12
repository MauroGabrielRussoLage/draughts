package ec.com.sofka.gateway.dto;

public class PlayerDTO {
    private String name;
    private String pieceColor;

    public PlayerDTO(String name, String pieceColor) {
        this.name = name;
        this.pieceColor = pieceColor;
    }

    public PlayerDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(String pieceColor) {
        this.pieceColor = pieceColor;
    }
}
