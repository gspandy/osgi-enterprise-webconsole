Ext.define('WebConsole.HelpWindow', {
  extend: 'Ext.window.Window',

  alias: 'widget.aboutwindow',
  plain: true,

  initComponent: function() {
    Ext.apply(this, {
        width: 400,
        height: 270,
        modal: true,
        title: 'About',
        layout: 'fit',
        html: '<br/><center><strong>OSGi Web Console - ${project.version}</strong></center>' +
              "<br/><center>Copyright 2010-2011 <a href='mailto:pacak.daniel@gmail.com'>Daniel Pacak</a>, Inc. All rights reserved.</center>",
        buttons: [{
            xtype: 'button',
            text: 'Close',
            scope: this,
            handler: this.destroy
        }]
    });

    this.callParent(arguments);
  }

});
