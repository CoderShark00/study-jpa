package jpabook.jpashop.domain;


import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {
    // 값 타입은 immutable 하기 때문에 기본생성자를 protected라고하는게 나은듯

    private String city;
    private String street;
    private String zipcode;


    protected Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
