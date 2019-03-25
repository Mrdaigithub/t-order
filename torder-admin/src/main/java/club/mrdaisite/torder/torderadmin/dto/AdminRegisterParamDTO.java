package club.mrdaisite.torder.torderadmin.dto;

/**
 * AdminRegisterParamDTO
 *
 * @author dai
 * @date 2019/03/25
 */
public class AdminRegisterParamDTO {
    private String username;

    private String password;

    private String backCard;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBackCard() {
        return backCard;
    }

    public void setBackCard(String backCard) {
        this.backCard = backCard;
    }

    @Override
    public String toString() {
        return "AdminRegisterParamDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", backCard='" + backCard + '\'' +
                '}';
    }
}
