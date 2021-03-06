Ext.define('WebConsole.Extension.WeatherWindow', {
  extend: 'Ext.window.Window',

  //alias: 'widget.hello.hellowindow',
  plain: true,

  initComponent: function() {
    Ext.apply(this, {
        width: 640,
        height: 480,
        modal: true,
        title: 'Weather',
        layout: 'fit',
        html: '<br/><center><strong>Weather service!</strong></center>',
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
