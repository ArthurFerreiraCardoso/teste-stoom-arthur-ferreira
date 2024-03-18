package br.com.stoom.store.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean active = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    private List<Product> products;

}
