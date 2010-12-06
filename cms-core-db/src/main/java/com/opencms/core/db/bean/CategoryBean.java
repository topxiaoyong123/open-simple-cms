package com.opencms.core.db.bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-6
 * Time: 20:33:53
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "cms_category")
public class CategoryBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String name;

    private String keywords;

    private String description;

}
