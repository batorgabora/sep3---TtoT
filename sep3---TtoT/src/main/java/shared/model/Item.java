package shared.model;

import java.time.LocalDate;

public class Item
{
  private int id;
  private String name;
  private String description;
  private int categoryId;
  private LocalDate uploadDate;
  private String location;
  private Double latitude;
  private Double longitude;
  private int uploaderId;

  public Item(int id, String name, String description, int categoryId, LocalDate uploadDate,
      String location, Double latitude, Double longitude, int uploaderId)
  {
    this.id = id;
    this.name = name;
    this.description = description;
    this.categoryId = categoryId;
    this.uploadDate = uploadDate;
    this.location = location;
    this.latitude = latitude;
    this.longitude = longitude;
    this.uploaderId = uploaderId;
  }

  public Item(String name, String description, int categoryId, LocalDate uploadDate,
      String location, Double latitude, Double longitude, int uploaderId)
  {
    this(0, name, description, categoryId, uploadDate, location, latitude, longitude, uploaderId);
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public int getCategoryId()
  {
    return categoryId;
  }

  public void setCategoryId(int categoryId)
  {
    this.categoryId = categoryId;
  }

  public LocalDate getUploadDate()
  {
    return uploadDate;
  }

  public void setUploadDate(LocalDate uploadDate)
  {
    this.uploadDate = uploadDate;
  }

  public String getLocation()
  {
    return location;
  }

  public void setLocation(String location)
  {
    this.location = location;
  }

  public Double getLatitude()
  {
    return latitude;
  }

  public void setLatitude(Double latitude)
  {
    this.latitude = latitude;
  }

  public Double getLongitude()
  {
    return longitude;
  }

  public void setLongitude(Double longitude)
  {
    this.longitude = longitude;
  }

  public int getUploaderId()
  {
    return uploaderId;
  }

  public void setUploaderId(int uploaderId)
  {
    this.uploaderId = uploaderId;
  }

  @Override
  public String toString()
  {
    return "Item{id=" + id + ", name='" + name + "', description='" + description
        + "', categoryId=" + categoryId + ", uploadDate=" + uploadDate
        + ", location='" + location + "', latitude=" + latitude + ", longitude=" + longitude
        + ", uploaderId=" + uploaderId + "}";
  }
}