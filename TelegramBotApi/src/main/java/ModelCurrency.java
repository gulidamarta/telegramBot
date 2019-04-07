public class ModelCurrency {
    private String Cur_Abbreviation;
    public void setCur_Abbreviation(String Cur_Abbreviation){
        this.Cur_Abbreviation = Cur_Abbreviation;
    }

    public String getCur_Abbreviation(){
        return Cur_Abbreviation;
    }

    private String Cur_Name;
    public void setCur_Name(String Cur_Name){
        this.Cur_Name = Cur_Name;
    }

    public String getCur_Name(){
        return Cur_Name;
    }

    private Double Cur_OfficialRate;
    public void setCur_OfficialRate(Double Cur_OfficialRate){
        this.Cur_OfficialRate = Cur_OfficialRate;
    }

    public Double getCur_OfficialRate(){
        return Cur_OfficialRate;
    }

    private int Cur_Scale;
    public void setCur_Scale(int Cur_Scale){
        this.Cur_Scale = Cur_Scale;
    }

    public int getCur_Scale(){
        return Cur_Scale;
    }
}
