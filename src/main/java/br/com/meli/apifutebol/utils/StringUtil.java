package br.com.meli.apifutebol.utils;

import br.com.meli.apifutebol.dto.ClubeDto;
import br.com.meli.apifutebol.model.Clube;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class StringUtil {

    // qtd string
    public static boolean isLengthBetween(String s, int min, int max) {
        if (s == null) {
            return false;
        }
        int len = s.length();
        return len >= min && len <= max;
    }
    public static boolean checkDate(LocalDate date){
        if (date == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        // retorna true se date for igual ou antes de today (data <= hoje)
        return !date.isAfter(today);
    }
    public static boolean isUFValid(String uf){
        if(uf==null)
            return  false;
        try{
            // Verifica parametro com a Lista fixa
            Enum.UF.valueOf(uf.toUpperCase());
            return true;
        }catch (IllegalArgumentException ex){
            return false;
        }
    }

}
