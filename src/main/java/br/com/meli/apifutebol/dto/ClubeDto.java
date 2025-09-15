package br.com.meli.apifutebol.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class ClubeDto {
    private long id;
    private String clubName;
    private String uf;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String creatAt ;
    private boolean status;

    public ClubeDto(){

    }
    public ClubeDto(Long id,
                    String clubName,
                    String uf,
                    String creatAt,
                    boolean status) {
        this.id = id;
        this.clubName = clubName;
        this.uf = uf;
        this.creatAt = creatAt;
        this.status = status;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getClubName() {
        return clubName;
    }
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }
    public LocalDate getCreatAt() {
        return LocalDate.parse(creatAt);
    }
    public void setCreatAt(String creatAt) {
        this.creatAt = creatAt;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}
