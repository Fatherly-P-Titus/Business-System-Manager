package SchoolManager;

public class Expense implements SchoolObject{
    
    String ID;
    String item;
    
    int amount;
    double unit_cost;
    double total_cost;
    
    String note;
    
    public Expense(){
        this.item = "NONE";
        this.note = "NONE";
        this.unit_cost = 0.0;
        this.total_cost = 0.0;
        this.amount = 0;
        
        generateID();
    }
    
    public Expense(String item,int amnt,double unit,String note){
        this.item = item;
        this.amount = amnt;
        this.unit_cost = unit;
        
        this.total_cost = unit_cost * amount;
        
        this.note = note;
        
        generateID();
    }
        
    public Expense(String csv){
        String[] dta = csv.split(",");
        
        this.ID = dta[0];
        this.item = dta[1];
        this.amount = Integer.parseInt(dta[2]);
        this.unit_cost = Double.parseDouble(dta[3]);
        this.total_cost = Double.parseDouble(dta[4]);
        this.note = dta[5];
    }
    
    
    public void setObject(String csv){
        
    }
    
    public Expense getObject(){
        return new Expense();
    }
    
    
    public String getItem(){
        return this.item;
    }
    
    public String getID(){
        return this.ID	;
    }
    
    public String getNote(){
        return this.note;
    }
    
    public double getUnitCost(){
        return this.unit_cost;
    }
    
    public double getTotalCost(){
        return this.total_cost;
    }
    
    public int getAmount(){
        return this.amount;
    }
    
    public void setItem(String item){
        this.item = item;
    }
    
    public void setID(String id){
        this.ID = id;
    }
    
    public void setNote(String note){
        this.note = note;
    }
    
    public void setAmount(int amnt){
        this.amount = amnt;
    }
    
    public void setUnitCost(double un){
        this.unit_cost = un;
        if((this.amount != 0)){
            this.total_cost = amount * un;
        }
    }
    
    public void setTotalCost(double cost){
        if((this.amount != 0) && (this.unit_cost != 0.0)){
            if((this.amount * this.unit_cost) != cost){
                this.total_cost = this.amount * this.unit_cost;
            }
        }else{
        this.total_cost = cost;
        }
    }
    
    
    private void generateID(){
        String id = "";
        
        for(int i = 0; i < 9; i++){
            id += String.format("%s",(int)(Math.random()*9));
        }
        this.ID = id;
    }
    
    
    public String infoString(){
        return String.format("\n•) ITEM: %s\n•) AMOUNT: %s\n•) UNIT COST: %s\n•) TOTAL COST: %s\n•) NOTE: %s\n",this.item,this.amount,this.unit_cost,this.total_cost,this.note);
    }
    
    @Override
    public String toString(){
        return String.format("%s,%s,%s,%s,%s,%s",this.ID,this.item,this.amount,this.unit_cost,this.total_cost,this.note);
    }
    
    @Override
    public boolean equals(Object obj){
        Expense ob = (Expense) obj;
        
        if((ob.getItem().equals(this.item)) && (ob.getID().equals(this.ID))){
            return true;
        }else
            return false;
    }
}




















