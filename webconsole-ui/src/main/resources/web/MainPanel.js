Ext.define('WebConsole.MainPanel', {
  extend: 'Ext.panel.Panel',

  alias: 'widget.mainpanel',

  initComponent: function(){
    Ext.apply(this, {
      layout: 'border',
      items: [
        this.createBundleTreePanel()
        //this.createMapPanel()
      ],
      dockedItems: this.createToolbar()
    });

    this.callParent(arguments);
  },

	  createToolbar : function() {
		this.toolbar = Ext.create('widget.toolbar', {
			items : [ {
				text : 'Reload',
				handler : this.onReloadClick,
				scope : this
			}, {
				text : 'Install',
				handler : this.onBundleInstallClick,
				scope : this
			}, {
				text : 'Extensions',
				handler : this.onExtensionsClick,
				scope : this
			}, '->', {
				text : 'About',
				handler : this.onAboutClick,
				scope : this
			} ]
		});

		return this.toolbar;
	},

  createBundleTreePanel: function() {
    this.networkPanel = Ext.create('widget.bundletreepanel', {
      region: 'west',
      padding: '5 0 5 5',
      width: 350,
      listeners: {
        itemclick: this.onBundleClick,
        itemdblclick: this.onBundleDblClick,
        scope: this
      }
    });
    return this.networkPanel;
  },

  onBundleClick: function(view, record, item, index, e, eOpts) {
    var rawId = record.get('id');
    /*if (rawId) {
      var id = parseInt(rawId.substring(2));
      if (rawId.charAt(0) == 'n') {
        this.onNetworkNodeClicked(id);
      } else if (rawId.charAt(0) == 'e') {
        this.onNetworkEdgeClicked(id);
      }
    }*/
  },

  onBundleDblClick: function(view, record, item, index, e, eOpts) {
    var bundleId = record.get('id');
    if (bundleId) {
      this.readBundle(bundleId);
    }
  },

  // @param nodeId node identifier
  readBundle: function(bundleId) {

    Ext.Ajax.request({
        url: 'service/bundles/read',
        params: {
          bundleId: bundleId
        },
        success: this.onReadBundleSuccess,
        failure: this.onReadBundleFailure,
        scope: this
      });
  },

  onReadBundleSuccess: function(response) {
	  alert(response.responseText);
    var values = Ext.JSON.decode(response.responseText);
    var win = Ext.create('widget.bundleinfowindow');
    win.setFieldValues(values);
    win.show();
  },

  onReadBundleFailure: function() {
    Ext.MessageBox.show({
      title: 'Application Error',
      msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
      buttons: Ext.MessageBox.OK,
      icon: Ext.MessageBox.ERROR
    });
  },

  onReloadClick: function() {
    this.networkPanel.update();
  },

  onBundleInstallClick: function() {
    var win = Ext.create('widget.bundleinstallwindow', {
      listeners: {
        scope: this,
        bundleinstalled: this.onBundleInstalled
      }
    });

    win.show();
  },

  onBundleInstalled: function(win) {
    this.networkPanel.update();
  },


  onAddNodeClick: function() {
    var win = Ext.create('widget.nodewindow', {
        listeners: {
          scope: this,
          nodecreated: this.onNodeCreated
        }
    });

    win.show();
  },


  onAddEdgeClick: function() {
    // Before showing Edge Dialog fetch available nodes
    Ext.Ajax.request({
      url: 'AvailableNodes.do',
      success: this.onAddEdgeSuccess,
      failure: this.onAddEdgeFailure,
      scope: this
    });
  },

  onAddEdgeSuccess: function(response) {
    var nodes = Ext.JSON.decode(response.responseText);
    var win = Ext.create('widget.edgewindow', {
      listeners: {
        edgecreated: this.onEdgeCreated,
        scope: this
      }
    });

    win.setSource(nodes);
    win.setTarget(nodes);
    win.show();
  },

  onAddEdgeFailure: function() {
    Ext.MessageBox.show({
      title: 'Application Error',
      msg: 'There was a problem processing your request. Please try again later or contact your system administrator.',
      buttons: Ext.MessageBox.OK,
      icon: Ext.MessageBox.ERROR
    });
  },

  onEdgeCreated: function(win) {
    this.networkPanel.update();
    this.mapPanel.update();
  },

  onAboutClick: function() {
    var win = Ext.create('widget.aboutwindow');
    win.show();
  },
  
  onExtensionsClick : function() {
	// Set up a model to use in our Store
	  Ext.define('Extension', {
	      extend: 'Ext.data.Model',
	      fields: [
	          {name: 'name', type: 'string'},
	          {name: 'desc',  type: 'string'}
	      ]
	  });
	  var myStore = Ext.create('Ext.data.Store', {
		    model: 'Extension',
		    proxy: {
		        type: 'ajax',
		        url : 'service/extensions',
		        reader: {
		            type: 'json'
		        }
		    },
		    autoLoad: true
		});
	  
		var grid = Ext.create('Ext.grid.Panel', {
		    //title: 'Simpsons',
			bodyBorder: false,
			sortableColumns: false,
		    store: myStore,
		    columns: [
		        { header: 'Name',  dataIndex: 'name' },
		        { header: 'Description', dataIndex: 'desc', flex: 1 },
		        
		        {xtype:'actioncolumn',
	            width:50,
	            items: [{
                    icon   : 'delete.gif',  // Use a URL in the icon config
                    tooltip: 'Sell stock',
                    handler: function(grid, rowIndex, colIndex) {
                        var rec = myStore.getAt(rowIndex);
                        alert("Run [" + rec.get('name') + "] extension..");
                    }
                } ]
		        }
		        
		    ]
		}); 
		
		 var win = Ext.create('Ext.window.Window', {
			    title: 'Installed Extensions',
			    bodyBorder: false,
			    height: 200,
			    width: 400,
			    layout: 'fit'
			});
		 
		 win.add(grid);
		 win.show();
  },
  
  onExtensionsClick2 : function() {
	 //alert('show extensions');
	  var win = Ext.create('Ext.window.Window', {
		    title: 'Installed Extensions',
		    height: 200,
		    width: 400,
		    layout: 'fit'
		});
	  
	  var fc = Ext.create('Ext.form.Panel', {
		  bodyPadding : '12 10 10',
			border : false,
			unstyled : true
	  });
	  
	  var textField1 = Ext.create('Ext.form.field.Text', {fieldLabel : 'My Text Field'});
	  
	  fc.add(textField1);
	  
	  win.add(fc);
	  //win.addDocked(button);
	  win.show();
  }


});