Ext.define('WebConsole.BundleInfoWindow', {
	extend : 'Ext.window.Window',

	alias : 'widget.bundleinfowindow',
	plain : true,

	initComponent : function() {
		this.form = Ext.create('widget.form', {
			bodyPadding : '12 10 10',
			border : false,
			unstyled : true,
			items : [ {
				xtype : 'textfield',
				anchor : '100%',
				name : 'bundleId',
				fieldLabel : 'Bundle Id',
				labelWidth : 120
			}, {
				xtype : 'textfield',
				anchor : '100%',
				name : 'symbolicName',
				fieldLabel : 'Symbolic Name',
				labelWidth : 120
			}, {
				xtype : 'textfield',
				anchor : '100%',
				name : 'version',
				fieldLabel : 'Version',
				labelWidth : 120
			}, {
				xtype : 'textfield',
				anchor : '100%',
				name : 'location',
				fieldLabel : 'Bundle Location',
				labelWidth : 120
			} ]
		});

		Ext.apply(this, {
			width : 350,
			title : 'Bundle',
			modal : true,
			layout : 'fit',
			items : this.form,
			buttons : [ {
				xtype : 'button',
				text : 'Save',
				scope : this,
				handler : this.onAddClick
			}, {
				xtype : 'button',
				text : 'Cancel',
				scope : this,
				handler : this.destroy
			} ]
		});
		this.callParent(arguments);
	},

	setFieldValues : function(values) {
		this.form.getForm().setValues(values);
	}

});
