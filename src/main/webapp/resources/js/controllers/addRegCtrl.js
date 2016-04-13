app.controller('AddRegCtrl', ['$scope', 'personService', 'regService',  function ($scope, personService, regService) {
    $scope.tabs = [
        { id: '1', title:'Enkeltpåmelding', content:'resources/jsp/singleReg.jsp' },
        { id: '2', title:'Gruppepåmelding', content:'resources/jsp/groupReg.jsp'}
    ];
    $scope.tabs.activeTab = 'Gruppepåmelding';
    $scope.selectedEvents = [];
    $scope.selectedSessions = [];
    $scope.selectedDays = [];

    $scope.courses = [];
    $scope.course = [];
    $scope.dateArray = [];

    $scope.persons = personService.get();
    $scope.$on('personSet', function(event, data){
        $scope.persons = data;
    });
    $scope.person = [];

    $scope.attendDate = [];

    $scope.tooltip = {title: 'Fjern romkamerat'};
    $scope.firstPersonRoom = {};
    $scope.hasRoom = personService.getHasRoom();


    $scope.saveGroupRegistration = function(workplace){ // Må sende med course.id, course.form, session, workplace, person, pris.
        var registration = {};
        registration.registrationID = 'Hei';
        registration.check = 'beeesj';
        //var form = $scope.course.form;
        //$scope.course.form = undefined;
        //$scope.sendAll($scope.course, form);
        self.sendRegistration(registration);
        //$scope.sendRegistration(registration);

        //registration.persons = $scope.persons;
        //registration.events = $scope.selectedEvents;

        /*registration.sessionsToAttend = [1];
        registration.workplace = workplace;
        registration.cost = $scope.findPrice();
        registration.dates = $scope.selectedDays; */

       /* var form = $scope.course.form;
        $scope.course.form = undefined;
        //$scope.sendAll($scope.course, form);

   /*     for (var i = 0; i<$scope.persons.length; i++){
            if ($scope.persons[i] !== 'undefined'){
                registration.registrationID = i;
               // registration.person = $scope.persons[i];
                $scope.sendRegistration(registration);
            }
        }*/
        //var test = {registrationID: 2};


        //sendAll($scope.course, $scope.course.form, registration);
    };

    $scope.saveSingleRegistration = function(person, workplace){ // Må sende med course.id, course.form, session, workplace, person, pris.
        // courseID, sessions[], events[], person, workplace, price[], datestoAttend[], optPersonalia, optWorkplace, extraInfo, alternativFakturaadresse, form
        var registration = {};
        registration.persons = person;
        registration.sessions = [1];
        registration.workplace = workplace;
        registration.events = $scope.selectedEvents;
        registration.price = $scope.findPrice();
        registration.selectedDays = $scope.selectedDays;
        var form = $scope.course.form;
        $scope.course.form = undefined;

        $scope.sendAll($scope.course, form);
    };

    $scope.sendAll = function(course, form){ // Funker ikke. Sender person-array, skal ikke gjøre det..
        regService.sendForm(form).then(function (successCallback) {
            console.log("form sent" + successCallback);
            regService.sendInfo(course).then(function (successCallback) {
                console.log("Course sent" + successCallback);
            }, function (errorCallback) {
                console.log("Error in regservice.sendInfo() " + errorCallback);
            })
        }, function (errorCallback) {
            console.log("Error in regservice.sendForm()" + errorCallback);
        });
    };

    self.sendRegistration = function(registration){
        console.log(registration);
        regService.sendRegistration(registration).then(function (successCallback){
            console.log("Registration sent " + successCallback);
        }, function (errorCallback){
            console.log("Error in sendRegistration" + errorCallback);
        });
    };

    $scope.findPrice = function(){ // Finn ut hvilke dager han skal delta på, multipliser med course.dagpakke og course.kursavgift.
        var ant = $scope.selectedDays.length;
        var price = [];
        if ($scope.allDaysCheck == true || $scope.selectedDays.length == $scope.dateArray.length){
            price[0] = {amount: $scope.course.courseFee, description: 'test'};
            price[1] = {amount: $scope.course.dayPackage * $scope.dateArray.length, description: 'test2'};
        } else {
            price[0] = {amount: $scope.course.courseSingleDayFee*ant, description: 'test'};
            price[1] = {amount: ($scope.course.dayPackage * ant), description: "test2"};
        }
        return price;
    };

    $scope.checkboxAccModel = {
        c1: false,
        rad: false,
        mark: false,
        another: false
    };

    $scope.update = function (newPerson) {
        personService.save(newPerson);
    };

    $scope.repeat = function (number) {
        var times = [];
        for (var i = 0; i < number; i++) {
            times.push(i)
        }
        return times;
    };

    $scope.removePerson = function(person){
        for (i = 0; i<$scope.persons.length; i++) {
            if ($scope.persons[i] === person) {
                personService.delete(person);
            }
        }
    };

    $scope.getPerson = function(id){
        return personService.getPerson(id);
    };

    $scope.getPersonName = function(id){
        var p = personService.getPerson(id);
        if (p == undefined) return null;
        else return  (personService.getPerson(id).firstname + " " + personService.getPerson(id).lastname);
    };

    $scope.removeRoom = function(person){
        personService.removeRoom(person);
    };

    $scope.hasRoommate = function(person){
        for (i = 0; i<$scope.persons.length; i++) {
            if ($scope.persons[i] === person) {
                if ($scope.persons[i].accomondation == undefined){
                    return false;
                }
            }
        }
        return true;
    };

    $scope.checkIfHasRoom = function(person){
        for (i = 0; i<$scope.hasRoom.length; i++){
            if ($scope.hasRoom[i] == person){
                return false;
            }
        }
        return true;
    };

    $scope.checkIfSelected = function(person){
        return person != $scope.firstPersonRoom;

    };


    $scope.saveRoom = function(acc, first, second){ // Her skal date også inn.
        console.log("Person: " + first.firstname + ",  " + first.personID);
        var accomondation = {};
        accomondation.startDate = acc.startDate;
        accomondation.endDate = acc.endDate;
        accomondation.hotelID = $scope.selectedAccomondation.id;
        if ($scope.checkboxAccModel.rad == true){ // Dobbeltrom.
            personService.hasRoom(first,second);
            personService.addRoommate(accomondation, first, second);
        } else { // Enkeltrom.
            personService.hasRoom(first);
            personService.addRoommate(accomondation, first);
        }
    };

    // Endre disse til å returnere true/false, likt som colorEvent() ? (Renere og mindre kode). (Legger til checkbox-value i en array, if -> true.
    $scope.wholeCourse = function wholeCourse() {
        for (var i = 0; i<$scope.dateArray; i++){
            $scope.selectedDays.push($scope.dateArray[i]);
            console.log(i + " Er checked: " + $scope.selectedDays[i]);
        }
    };

    $scope.selectDay = function selectDay(day) {  // http://stackoverflow.com/questions/14514461/angularjs-how-to-bind-to-list-of-checkbox-values
        var idx = $scope.selectedDays.indexOf(day);
        $scope.allDaysCheck = false;
        if (idx > -1) {
            $scope.selectedDays.splice(idx, 1);
        } else {
            $scope.selectedDays.push(day);
            if ($scope.selectedDays.length == $scope.dateArray.length){
                $scope.allDaysCheck = true;
            }
        }
    };

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
        if (c.form != null){
            course.form = c.form;
        }
        if (c.courseFee != null){
            course.courseFee = c.courseFee;
        }
        if (c.courseSingleDayFee != null){
            course.courseSingleDayFee = c.courseSingleDayFee;
        }
        if (c.dayPackage != null){
            course.dayPackage = c.dayPackage;
        }
        if (c.accomondations != null){
            course.accomondations = c.accomondations;
        }
        return course;
    };

    self.loadApplication();
    self.getCourseById(0);

    $scope.whichType = function(type){ // Returnerer true hvis "type" er input, false hvis "checkbox".
        if (type == "Input") {
            return true;
        } else return false;
    };

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

    $scope.colorAccomondation = function (accomondation) { // Skjekk om id finnes i selectedEvents.
        if (accomondation == $scope.selectedAccomondation) return true;
        else { return false }
    };

    $scope.selectAccomondation = function selectSession(accomondation) {
        $scope.selectedAccomondation = accomondation;
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