Ext.setup({
    onReady : function() {
        Ext.regModel('Event', {
            fields: ['title', 'startedAt', 'endedAt', 'eventUrl', 'eventId', 'place', 'address', 'eventSource']
        });

        // 一枚目の画面の設定
        var monthsList = new Array();
        monthsList.push({text: '向こう三ヶ月',  value: ''});

        // 6ヶ月分追加
        var currentDate = new Date();
        for(i=0; i<6; i++)
        {
           var addedDate = currentDate.add(Date.MONTH, i);
           var addedDateYyyyMm = addedDate.format("Y/m");
           monthsList.push({text: addedDateYyyyMm, value: addedDateYyyyMm});
        }

        // 00:00 ～ 23:30までのリストを作成
        var baseTime = new Date();
        baseTime.setHours(0);
        baseTime.setMinutes(0);
        var timeList = [];
        for(var i=0; i<48; i++)
        {
            var addedTime = baseTime.add(Date.MINUTE, i * 30);
            timeList.push({text: addedTime.format("H:i"), value: addedTime.format("H:i")});
        }

        var card1Config = {
            scroll: 'vertical',
            items: [
                {
                    xtype: 'fieldset',
                    title: 'イベント検索',
                    defaults: {
                        required: true,
                        labelAlign: 'left',
                        labelWidth: '100px'
                    },
                    items: [{
                        xtype: 'selectfield',
                        name : 'target_month',
                        label: '対象月',
                        options: monthsList,
                        value: currentDate.format("Y/m")
                    }, {
                        xtype: 'selectfield',
                        name : 'target_day_of_week',
                        label: '対象曜日',
                        options: [
                            {text: '平日',  value: '1'},
                            {text: '土日',  value: '2'},
                            {text: '全て',  value: '3'}
                        ],
                        value: '3'
                    }, {
                        xtype: 'selectfield',
                        name : 'target_time_from',
                        label: '時間From',
                        options: timeList,
                        value: '00:00'
                    }, {
                        xtype: 'selectfield',
                        name : 'target_time_to',
                        label: '時間To',
                        options: timeList,
                        value: '23:30'
                    }, {
                        xtype: 'textfield',
                        name : 'address',
                        label: '住所',
                        required: false
                    }, {
                        xtype: 'textfield',
                        name : 'keyword',
                        label: 'キーワード',
                        required: false
                    }, {
                        xtype: 'checkboxfield',
                        name : 'only_exists_vacant_seat',
                        label: '空席あり',
                        checked: true,
                        value: true
                    }, {
                        xtype: 'button',
                        name : 'search',
                        text: '検索',
                        handler: function() {
                            var baseUrl = "/search/";
                            params = card1.getValues();
                            params['output_format'] = 'json';

                            // 検索実施
                            Ext.Ajax.request({
                                url: baseUrl,
                                method: 'GET',
                                params: params,
                                success: function(response, opts) {
                                    // 検索結果をセット
                                    card2List.store.loadData(Ext.decode(response.responseText));

                                    // Loading...を隠す
                                    Ext.getBody().unmask();

                                    // 検索結果ページに遷移
                                    cardPanel.getLayout().next('fade', true);

                                    // スクロール位置を先頭にリセット
                                    card2List.scroller.scrollTo({x: 0, y: 0}, false);
                                }
                            });

                            // Loading...を表示
                            Ext.getBody().mask('Loading...', 'loading', false);
                        }
                    }]
                }
            ]
        };

        // 一枚目の画面
        var card1 = new Ext.form.FormPanel(card1Config);

        // 二枚目の画面用ビュー（リスト）
        var card2List = new Ext.List({
            itemTpl: '<tpl for="."><div class="event" style="height: auto">[{eventSource}]{title}<br>{startedAt}<br>～ {endedAt}</div></tpl>',
            itemSelector: 'div.event',
            singleSelect: true,
            store: new Ext.data.Store({
                model: 'Event'
            })
        });
        card2List.addListener("itemtap", function(dv, index, item, e) {
            var tapped = dv.getStore().getAt(index);

            // 選択されたイベントの情報
            var title = tapped.get('title');
            var startedAt = tapped.get('startedAt');
            var endedAt = tapped.get('endedAt');
            var place = tapped.get('place');
            var address = tapped.get('address');
            var eventUrl = tapped.get('eventUrl');
            var eventSource = tapped.get('eventSource');

            // 検索結果をセット
            var resultHtml = '<div id="event" class="event" style="background: #FFF"><ul>' + 
                '<li>■タイトル<br>[' + eventSource + ']' + title + '</li>' + 
                '<li>■開催時間<br>' + startedAt + '<br> ～ ' + endedAt + '</li>' + 
                '<li>■会場<br>' + place + '</li>' + 
                '<li>■住所<br>' + address + '</li>' + 
                '<li>■イベント詳細ページ<br><a href = "' + eventUrl + '" target = "_blank">Safariで開く</a></li>' + 
                '</ul></div>';

            card3Panel.update(resultHtml);

            // 詳細ページに遷移
            cardPanel.getLayout().next('fade', false);
        });

        // 戻るボタン用の設定
        backButtonConfig = {
            text: 'Back',
            ui: 'back',
            handler: function() { cardPanel.getLayout().prev('fade', true) },
            scope: this
        };

        // 二枚目の画面
        var card2 = new Ext.Panel({
            layout: 'fit',
            dockedItems: new Ext.Toolbar({
                ui: 'dark',
                dock: 'top',
                title: this.title,
                items: [new Ext.Button(backButtonConfig)]
            }),
            items: [card2List],
            scroll: 'vertical'
        });

        // 三枚目の画面用ビュー（パネル）
        var card3Panel = new Ext.Panel({
            html: '<div>card3</div>',
            scroll: 'vertical'
        });

        // 三枚目の画面
        var card3 = new Ext.Panel({
            layout: 'fit',
            dockedItems: new Ext.Toolbar({
                ui: 'dark',
                dock: 'top',
                title: this.title,
                items: [new Ext.Button(backButtonConfig)]
            }),
            items: [card3Panel]
        });

        var cardPanel = new Ext.Panel({
            //fullscreen: true,
            layout: 'card',
            items: [
                card1, card2, card3
            ],
            title: 'Search',
            iconCls: 'search'
        });

		var aboutPanel = new Ext.Panel({
			html: '<div id="event" class="event" style="background: #FFF"><ul>' + 
                '<li><a href = "http://atnd.org/">ATND</a> / <a href = "http://www.zusaar.com/">Zusaar</a> / <a href = "http://partake.in/">PARTAKE</a>のイベントをまとめて探せるサービスです。</li>' + 
                '<li>Created by <a href = "http://twitter.com/#!/taka_2/"　target = "_blank">@taka_2</a></li>' + 
                '</ul></div>',
			title: 'About',
			iconCls: 'info'
		});

        var panel = new Ext.TabPanel({
        	tabBar: {
        		dock: 'bottom',
        		layout: {
        			pack: 'center'
        		}
        	},
        	fullscreen: true,
        	ui: 'light',
        	cardSwitchAnimation: {
        		type: 'slide',
        		cover: true
        	},
        	defaults: {
        		scroll: 'vertical'
        	},
        	items: [
        		cardPanel,
        		aboutPanel
        	]
        });
    }
});
