package br.ufc.quixada.model;

public class Player {
  private Integer id;
  private String name;
  private boolean ia;

  public Player(String name, boolean ia) {
    this.name = name;
    this.ia = ia;
  }

  public Player(Integer id, String name, boolean ia) {
    this.id = id;
    this.name = name;
    this.ia = ia;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean getIa() {
    return ia;
  }

  public void setIa(boolean ia) {
    this.ia = ia;
  }

  public Integer getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Player other = (Player) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (ia != other.ia)
      return false;
    return true;
  }
}
