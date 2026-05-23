package model;

public class Customer extends Person {
    private int creditScore;

    public Customer(String name, String email, String phoneNumber, int creditScore) {
        super(name, email, phoneNumber);
        this.creditScore = creditScore;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }
    @Override
    public String toString() {
        return "Customer{" + super.toString() + ", credit score = " + creditScore + "}";
    }
}
