app.controller('AddRegCtrl', ['$scope', 'personService', 'regService',  function ($scope, personService, regService) {
    $scope.tabs = [
        { id: '1', title:'Enkeltpåmelding', content:'resources/jsp/singleReg.jsp' },
        { id: '2', title:'Gruppepåmelding', content:'resources/jsp/groupReg.jsp'}
    ];
    $scope.tabs.activeTab = 'Enkeltpåmelding';

    $scope.days = [{id: 'Mandag'}, {id: 'Tirsdag'}, {id: 'Onsdag'}];
    $scope.selectedEvents = [];
    $scope.selectedSessions = [];
    $scope.firstPersonRoom = {};
    $scope.persons = personService.get();
    $scope.hasRoom = personService.getHasRoom();
    $scope.courses = [];
    $scope.sessions = [];
    $scope.events = [];
    $scope.course = [];
    $scope.dateArray = [];


    self.loadApplication = function(){
        regService.getCourses().then(function(response) {
                $scope.courses = new Array();
                for (var i = 0; i < response.length; i++){
                    $scope.courses.push(self.setCourse(response[i]));
                    var date = new Date($scope.courses[i].startDate);
                }
            }, function(errorResponse){
                console.log("Error in loadApplication()");
            }
        )};

    self.getCourseById = function(id){
        regService.getCourse(id).then(function(response){
            $scope.course = self.setCourse(response);
            var currentDate = $scope.course.startDate;
            while (currentDate <= $scope.course.endDate) {
                $scope.dateArray.push(new Date(currentDate));
                currentDate = $scope.addDays(currentDate, 1)
            }
            regService.setCourse($scope.course, $scope.course.roles, $scope.dateArray);
        })
    };


    $scope.addDays = function(day, days) {
        var dat = new Date(day.valueOf());
        dat.setDate(dat.getDate() + days);
        return dat;
    };

    $scope.sameDate = function(d1, n2){
        var d2 = new Date(n2);
        if ((d1.getFullYear() == d2.getFullYear()) && d1.getDate() == d2.getDate()){
            return true;
        } else {
            false;
        }
    };

    self.setCourse = function(c){
        var course = {};
        if (c.title != null){
            course.title = c.title;
        }
        if (c.description != null){
            course.description = c.description;
        }
        if (c.startDate != null){
            course.startDate = c.startDate;
            //new Date((c.startDate).getTime());
        }
        if (c.endDate != null){
            course.endDate = c.endDate;
            //new Date(c.endDate.getTime());
        }
        if (c.id != null){
            course.id = c.id;
        }
        if (c.sessions != null){
            course.sessions = c.sessions;
        }
        if (c.events != null){
            course.events = c.events;
        }
        if (c.roles != null){
            course.roles = c.roles;
        }
        return course;
    };

    self.loadApplication();
    self.getCourseById(1);

    $scope.repeat = function (number) {
        var times = [];
        for (var i = 0; i < number; i++) {
            times.push(i)
        }
        return times;
    };

    $scope.colorEvent = function (event) { // Skjekk om id finnes i selectedEvents.
        for (i = 0; i < $scope.selectedEvents.length; i++) {
            if (event == $scope.selectedEvents[i]) {
                return true;
            }
        }
        return false;
    };

    $scope.colorSession = function (session) { // Skjekk om id finnes i selectedEvents.
        for (i = 0; i < $scope.selectedSessions.length; i++) {
            if (session == $scope.selectedSessions[i]) {
                return true;
            }
        }
        return false;
    };

    $scope.selectSession = function selectSession(session) {
        var idx = $scope.selectedSessions.indexOf(session);
        if (idx > -1) { // Blir unchecked.
            $scope.selectedSessions.splice(idx, 1);
        } else {
            var notOverlaps = true;
            for (i = 0; i<$scope.selectedSessions.length; i++){
                if ($scope.overlaps(session.startTime, session.endTime, $scope.selectedSessions[i].startTime, $scope.selectedSessions[i].endTime)){
                    notOverlaps = false;
                    break;
                }
            } if (notOverlaps) $scope.selectedSessions.push(session);
        }
    };

    $scope.selectEvent = function selectEvent(event) {
        var idx = $scope.selectedEvents.indexOf(event);
        // Blir unchecked.
        if (idx > -1) {
            $scope.selectedEvents.splice(idx, 1);
        }
        // Blir checked.
        else {
            $scope.selectedEvents.push(event);
        }
    };

    // OK Ser etter om to datoer/tidspunk overlapper hverandre. Brukes i sesjons-valget.
    $scope.overlaps = function(startA, endA, startB, endB) { // Hvis overlapper, return true. else false.
        if (startA <= startB && startB <= endA) return true; // b starts in a
        if (startA <= endB   && endB   <= endA) return true; // b ends in a
        if (startB <  startA && endA   <  endB) return true; // a in b
        return false;
    };

    $scope.selectedDate = new Date();
    $scope.fromDate = Date.UTC(2016, 1, 10);  // new Date();
    $scope.untilDate = Date.UTC(2016, 1, 15);
    $scope.minDate = Date.UTC(2016, 1, 9);  // new Date();
    $scope.maxDate = Date.UTC(2016, 1, 15);
    $scope.getType = function (key) {
        return Object.prototype.toString.call($scope[key]);
    };
}]);