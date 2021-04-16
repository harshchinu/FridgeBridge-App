package com.example.fridge_bridge.Model;

public class RecipeModel {
        public int id;
        public String title;
        public String image;

      /*  public List<MissedIngredient> missedIngredients;
        public List<UsedIngredient> usedIngredients;
        public List<UnusedIngredient> unusedIngredients;*/

    public RecipeModel(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }
}
