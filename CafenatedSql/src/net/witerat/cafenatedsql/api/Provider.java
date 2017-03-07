package net.witerat.cafenatedsql.api;

public interface Provider {
	public abstract SchemaFactory getScemaFactory();
	public abstract TableFactory getTableFactory(); 
	public abstract ViewFactory getViewFactory(); 
}
