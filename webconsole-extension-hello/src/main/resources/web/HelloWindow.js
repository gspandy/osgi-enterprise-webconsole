Ext.define('WebConsole.Extension.HelloWindow', {
  extend: 'Ext.window.Window',

  alias: 'widget.hello.hellowindow',
  plain: true,

  initComponent: function() {
    Ext.apply(this, {
        width: 640,
        height: 480,
        modal: true,
        title: 'Hello',
        layout: 'fit',
        html: '<br/><center><strong>Hi! I am OSGi Web Console Extension!</strong></center>' +
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
