package dk.via.sep3.ttot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category", schema = "ttot")
public class Category
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  protected Category()
  {
  }

  public Category(String name)
  {
    this.name = name;
  }

  public Integer getId() { return id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  @Override
  public String toString()
  {
    return "Category{id=" + id + ", name='" + name + "'}";
  }
}
