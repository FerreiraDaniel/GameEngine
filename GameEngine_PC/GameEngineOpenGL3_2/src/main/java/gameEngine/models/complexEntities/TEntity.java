package gameEngine.models.complexEntities;

import com.dferreira.commons.SEnum;

/**
 * Defines the type of entity 
 */
public enum TEntity implements SEnum{
	fern, tree, banana_tree, grass, flower, marble, player,
	
	;

	@Override
	public String getValue() {
		return this.toString();
	}

}
