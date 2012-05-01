Ext.define('WebConsole.BundleTreePanel', {
	extend : 'Ext.tree.Panel',

	alias : 'widget.bundletreepanel',

	initComponent : function() {
		this.treeStore = Ext.create('Ext.data.TreeStore', {
			proxy : {
				type : 'ajax',
				url : 'service/bundles/tree'
			}
		});
		Ext.apply(this, {
			store : this.treeStore,
			rootVisible : false
		});

		this.callParent(arguments);
	},

	update : function() {
		this.treeStore.load();
	},

	onDestroy : function() {
		this.callParent(arguments);
	}

});
