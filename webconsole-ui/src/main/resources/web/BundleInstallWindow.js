Ext.define('WebConsole.BundleInstallWindow', {
  extend: 'Ext.window.Window',

  alias: 'widget.bundleinstallwindow',
  plain: true,

  initComponent: function() {
    this.addEvents(
        'bundleinstalled'
    );

	this.form = Ext.create('widget.form', {
		bodyPadding : '12 10 10',
		border : false,
		unstyled : true,
		items : [ {
			anchor : '100%',
			xtype : 'filefield',
			allowBlank : false,
			id : 'form-file',
			fieldLabel : 'Bundle',
			name : 'xml-path',
			buttonText : 'Browse...'
		} ]
	});
        
        
        Ext.apply(this, {
            width: 350,
            title: 'Install Bundle',
            modal: true,
            layout: 'fit',
            items: this.form,
            buttons: [{
                xtype: 'button',
                text: 'Install',
                scope: this,
                handler: this.onInstallClick
            }, {
                xtype: 'button',
                text: 'Cancel',
                scope: this,
                handler: this.destroy
            }]
        });
        this.callParent(arguments);
    },
    
    onInstallClick: function() {
       if(this.form.getForm().isValid()){
            this.form.submit({
                url: 'service/bundles/install',
                waitMsg: 'Installing bundle...',
                scope: this,
                success: this.onInstallSuccess
            });
        }
    },

  onInstallSuccess: function(fp, o) {
    this.fireEvent('bundleinstalled', this);
    this.destroy();
  }

});