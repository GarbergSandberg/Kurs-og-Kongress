<%--
  Created by IntelliJ IDEA.
  User: eiriksandberg
  Date: 18.01.2016
  Time: 11.41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="addSessionsApp">
<head>
    <title>Registrer kurs</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
</head>
<body>
    <div ng-controller="addSessionsCtrl">
    <table>
        <tr>
            <td>Mandag</td>
            <td><button class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Registrer ny sesjon</button></td>
        </tr>
        <tr>
            <td>Tirsdag</td>
            <td><button class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Registrer ny sesjon</button></td>
        </tr>
    </table>
        <p>State = {{state}}</p>
    <script src="addSessionsApp.js"></script>
    <script src="addSessionsCtrl.js"></script>

<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Registrer ny sesjon</h4>
            </div>
            <div class="modal-body">
                <form>
                    <fieldset class="form-group">
                        <label for="title">Tittel:</label>
                        <input ng-model="session.title" type="text" class="form-control" id="title">
                    </fieldset>
                    <fieldset class="form-group">
                        <label for="description">Beskrivelse:</label>
                        <textarea ng-model="session.description" class="form-control" id="description" rows="2"></textarea>
                    </fieldset>
                    <fieldset class="form-group">
                        <label for="startTime">Klokkeslett start:</label>
                        <select ng-model="session.startTime" class="form-control" id="startTime">
                            <option>00.00</option>
                            <option>01.00</option>
                            <option>02.00</option>
                            <option>03.00</option>
                            <option>04.00</option>
                            <option>05.00</option>
                            <option>06.00</option>
                            <option>07.00</option>
                            <option>08.00</option>
                            <option>09.00</option>
                            <option>10.00</option>
                            <option>11.00</option>
                            <option>12.00</option>
                            <option>13.00</option>
                            <option>14.00</option>
                            <option>15.00</option>
                            <option>16.00</option>
                            <option>17.00</option>
                            <option>18.00</option>
                            <option>19.00</option>
                            <option>20.00</option>
                            <option>21.00</option>
                            <option>22.00</option>
                            <option>23.00</option>
                            <option>24.00</option>
                        </select>
                    </fieldset>
                    <fieldset class="form-group">
                        <label for="endTime">Klokkeslett slutt:</label>
                        <select ng-model="session.endtime" class="form-control" id="endTime">
                            <option>00.00</option>
                            <option>01.00</option>
                            <option>02.00</option>
                            <option>03.00</option>
                            <option>04.00</option>
                            <option>05.00</option>
                            <option>06.00</option>
                            <option>07.00</option>
                            <option>08.00</option>
                            <option>09.00</option>
                            <option>10.00</option>
                            <option>11.00</option>
                            <option>12.00</option>
                            <option>13.00</option>
                            <option>14.00</option>
                            <option>15.00</option>
                            <option>16.00</option>
                            <option>17.00</option>
                            <option>18.00</option>
                            <option>19.00</option>
                            <option>20.00</option>
                            <option>21.00</option>
                            <option>22.00</option>
                            <option>23.00</option>
                            <option>24.00</option>
                        </select>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">
                <button ng-click="saveSession()" type="submit" class="btn btn-default" data-dismiss="modal">Lagre</button>
            </div>
        </div>

    </div>
</div>
</body>
</html>
