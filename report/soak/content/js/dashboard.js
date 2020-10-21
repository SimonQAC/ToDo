/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 99.93970164373319, "KoPercent": 0.06029835626680817};
    var dataset = [
        {
            "label" : "KO",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "OK",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.9586664817517074, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.9565153281186367, 500, 1500, "\/user\/create"], "isController": false}, {"data": [0.9639196305370167, 500, 1500, "\/task\/read\/id"], "isController": false}, {"data": [0.9628640297707987, 500, 1500, "\/user\/delete"], "isController": false}, {"data": [0.9634365690517678, 500, 1500, "\/user\/read\/id"], "isController": false}, {"data": [0.953604768867394, 500, 1500, "\/user\/update"], "isController": false}, {"data": [0.9533409407886395, 500, 1500, "\/task\/update"], "isController": false}, {"data": [0.9569909113931174, 500, 1500, "\/task\/create"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 497526, 300, 0.06029835626680817, 147.32339013438414, 0, 4883, 69.0, 460.0, 724.9500000000007, 1270.9900000000016, 1912.0906994619525, 546.5973815214259, 444.19252317928516], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions\/s", "Received", "Sent"], "items": [{"data": ["\/user\/create", 71209, 45, 0.0631942591526352, 155.28496397927333, 0, 3629, 70.0, 446.0, 714.0, 1245.950000000008, 273.6734089939546, 86.45922383448887, 68.37511577766077], "isController": false}, {"data": ["\/task\/read\/id", 71022, 39, 0.054912562304638, 135.16811692151774, 0, 4883, 68.0, 370.0, 639.9500000000007, 1154.9900000000016, 272.9673117205066, 78.72750098487614, 50.428102089618925], "isController": false}, {"data": ["\/user\/delete", 70942, 33, 0.046516872938456764, 137.0428378111682, 1, 3856, 69.0, 386.0, 637.9500000000007, 1144.9900000000016, 272.6682220180877, 51.10391945915665, 56.76456781705338], "isController": false}, {"data": ["\/user\/read\/id", 71164, 36, 0.05058737563936822, 135.77579956157743, 0, 3843, 68.0, 384.0, 638.0, 1123.9900000000016, 273.5025653836546, 86.29944683914948, 50.529564515315435], "isController": false}, {"data": ["\/user\/update", 71128, 50, 0.07029580474637273, 158.22583230232576, 1, 3674, 70.0, 461.0, 717.0, 1274.950000000008, 273.3663088553497, 86.40999331505844, 64.09965015752731], "isController": false}, {"data": ["\/task\/update", 70983, 41, 0.05776030880633391, 159.31679416198355, 1, 3854, 70.0, 471.0, 737.0, 1277.900000000016, 272.8195154948632, 78.69369152553203, 80.90209808456933], "isController": false}, {"data": ["\/task\/create", 71078, 56, 0.07878668505022651, 150.42779763077218, 0, 3563, 69.0, 443.0, 693.0, 1276.9900000000016, 273.1793933617231, 78.92642246303635, 73.11349074395245], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Non HTTP response code: java.net.SocketException\/Non HTTP response message: Socket closed", 300, 100.0, 0.06029835626680817], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 497526, 300, "Non HTTP response code: java.net.SocketException\/Non HTTP response message: Socket closed", 300, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["\/user\/create", 71209, 45, "Non HTTP response code: java.net.SocketException\/Non HTTP response message: Socket closed", 45, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["\/task\/read\/id", 71022, 39, "Non HTTP response code: java.net.SocketException\/Non HTTP response message: Socket closed", 39, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["\/user\/delete", 70942, 33, "Non HTTP response code: java.net.SocketException\/Non HTTP response message: Socket closed", 33, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["\/user\/read\/id", 71164, 36, "Non HTTP response code: java.net.SocketException\/Non HTTP response message: Socket closed", 36, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["\/user\/update", 71128, 50, "Non HTTP response code: java.net.SocketException\/Non HTTP response message: Socket closed", 50, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["\/task\/update", 70983, 41, "Non HTTP response code: java.net.SocketException\/Non HTTP response message: Socket closed", 41, null, null, null, null, null, null, null, null], "isController": false}, {"data": ["\/task\/create", 71078, 56, "Non HTTP response code: java.net.SocketException\/Non HTTP response message: Socket closed", 56, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
