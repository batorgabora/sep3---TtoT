package dk.via.sep3.ttot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "item", schema = "ttot")
public class Item
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String description;

  @Column(name = "category_id")
  private Integer categoryId;

  @Column(name = "upload_date")
  private LocalDate uploadDate;

  private String location;
  private Double latitude;
  private Double longitude;

  @Column(name = "uploader_id")
  private Integer uploaderId;

  protected Item()
  {
  }

  public Item(String name, String description, Integer categoryId, LocalDate uploadDate,
      String location, Double latitude, Double longitude, Integer uploaderId)
  {
    this.name = name;
    this.description = description;
    this.categoryId = categoryId;
    this.uploadDate = uploadDate;
    this.location = location;
    this.latitude = latitude;
    this.longitude = longitude;
    this.uploaderId = uploaderId;
  }

  public Integer getId() { return id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }
  public Integer getCategoryId() { return categoryId; }
  public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
  public LocalDate getUploadDate() { return uploadDate; }
  public void setUploadDate(LocalDate uploadDate) { this.uploadDate = uploadDate; }
  public String getLocation() { return location; }
  public void setLocation(String location) { this.location = location; }
  public Double getLatitude() { return latitude; }
  public void setLatitude(Double latitude) { this.latitude = latitude; }
  public Double getLongitude() { return longitude; }
  public void setLongitude(Double longitude) { this.longitude = longitude; }
  public Integer getUploaderId() { return uploaderId; }
  public void setUploaderId(Integer uploaderId) { this.uploaderId = uploaderId; }

  @Override
  public String toString()
  {
    return "Item{id=" + id + ", name='" + name + "', categoryId=" + categoryId
        + ", uploadDate=" + uploadDate + ", location='" + location
        + "', latitude=" + latitude + ", longitude=" + longitude
        + ", uploaderId=" + uploaderId + "}";
  }
}
