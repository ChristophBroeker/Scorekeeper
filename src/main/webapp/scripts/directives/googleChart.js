/**
 * Created with JetBrains WebStorm.
 * User: andre.marbeck
 * Date: 17.06.13
 * Time: 10:21
 * To change this template use File | Settings | File Templates.
 */

ScoreKeeper.directive('googleChart', function ($timeout) {
    return {
        restrict: 'A',
        scope: {
            chart: '=chart'
        },
        link: function ($scope, $elm, $attr) {
            // Watches, to refresh the chart when its data, title or dimensions change
            $scope.$watch('chart', function () {
                draw();
            }, true); // true is for deep object equality checking

            function draw() {
                if (!draw.triggered && ($scope.chart != undefined)) {
                    draw.triggered = true;
                    $timeout(function () {
                        draw.triggered = false;

                        var dataTable = new google.visualization.DataTable($scope.chart.data, 0.5);

                        var chartWrapperArgs = {
                            chartType: $scope.chart.type,
                            dataTable: dataTable,
                            options: $scope.chart.options,
                            containerId: $elm[0]
                        };

                        var chartWrapper = new google.visualization.ChartWrapper(chartWrapperArgs);
                        google.visualization.events.addListener(chartWrapper, 'ready', function () {
                            $scope.chart.displayed = true;
                        });
                        google.visualization.events.addListener(chartWrapper, 'error', function (err) {
                            console.log("Chart not displayed due to error: " + err.message);
                        });
                        $timeout(function () {
                            chartWrapper.draw();
                        });
                    }, 0, true);
                }
            }

        }
    };
});