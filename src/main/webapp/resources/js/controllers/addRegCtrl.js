app.controller('AddRegCtrl', ['$scope', 'personService', 'regService',  function ($scope, personService, regService) {
    $scope.tabs = [
        { id: '1', title:'Enkeltpåmelding', content:'resources/jsp/singleReg.jsp' },
        { id: '2', title:'Gruppepåmelding', content:'resources/jsp/groupReg.jsp'}
    ];
    $scope.tabs.activeTab = 'Gruppepåmelding';
    $scope.registration = {};
    $scope.registrations = regService.get();
    $scope.selectedEvents = [];
    $scope.selectedSessions = [];
    $scope.selectedDays = [];
    $scope.courses = [];
    $scope.course = {};
    $scope.$on('courseSet', function(event, data){
        $scope.course = data;
    });
    $scope.dateArray = [];
    $scope.persons = personService.get();
    $scope.$on('personSet', function(event, data){
        $scope.persons = data;
    });
    $scope.person = [];
    $scope.attendDate = [];
    $scope.tooltip = {title: 'Fjern romkamerat'};
    $scope.firstPersonRoom = {};
    $scope.secondPersonRoom = {};
    $scope.hasRoom = [];

    $scope.newacc = {};

    $scope.saveGroupRegistration = function(workplace){ // Må sende med course.id, course.form, session, workplace, person, pris.
        /*var registration = {};
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

    $scope.saveSingleRegistration = function(registration){ // Må sende med course.id, course.form, session, workplace, person, pris.
        // courseID, sessions[], events[], person, workplace, price[], datestoAttend[], optPersonalia, optWorkplace, extraInfo, alternativFakturaadresse, form
        var optionals = self.inputParameterResolver(registration);
        registration.optionalPersonalia = optionals.optionalPersonalia;
        registration.optionalWorkplace = optionals.optionalWorkplace;
        registration.extraInfo = optionals.extraInfo;
        registration.cost = self.findPrice($scope.selectedDays.length, $scope.allDaysCheck);//
        registration.sessionsToAttend = self.resolveID($scope.selectedSessions);
        registration.eventsToAttend = self.resolveID($scope.selectedEvents);
        registration.course = $scope.course;
        registration.dates = $scope.selectedDays;
        self.sendRegistration(registration);
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

    self.findPrice = function(ant, allDaysCheck){ // Finn ut hvilke dager han skal delta på, multipliser med course.dagpakke og course.kursavgift.
        var price = [];
        console.log($scope.dateArray.length + " dateArray.length");
        if (allDaysCheck == true || ant == $scope.dateArray.length){
            price.push({amount: $scope.course.courseFee, description: 'Kursavgift'});
            for (var i = 0; i < $scope.dateArray.length; i++){
                price.push({amount: $scope.course.dayPackage, description: 'Dagpakke'});
            }
        } else {
            price.push({amount: $scope.course.courseSingleDayFee*ant, description: 'Kursavgift'});
            for (var u = 0; u < ant; u++){
                price.push({amount: ($scope.course.dayPackage), description: "Dagpakke"})
            }
        }
        return price;
    };

    $scope.checkboxAccModel = {
        c1: false,
        rad: false,
        mark: false,
        another: false
    };

    $scope.update = function (registrations) { // Sender med et registrations-objekt som inneholder en person hver.
        regService.saveReg(registrations);
        personService.save(registrations);
    };

    $scope.repeat = function (number) {
        var times = [];
        for (var i = 0; i < number; i++) {
            times.push(i)
        }
        return times;
    };

    $scope.removePerson = function(person){
        for (i = 0; i<$scope.registrations.length; i++) {
            if ($scope.registrations.persons[i] === person) {
                personService.delete(person);
            }
        }
    };

    $scope.getPerson = function(id){
        return personService.getPerson(id);
    };

    $scope.getPersonName = function(id){
        return regService.getPersonName(id);
        /*var p = personService.getPerson(id);
        if (p == undefined) return null;
        else return  (personService.getPerson(id).firstname + " " + personService.getPerson(id).lastname); */
    };

    $scope.removeRoom = function(reg){
        var idx = $scope.hasRoom.indexOf(reg.person.personID);
        $scope.hasRoom.splice(idx,1);
        if (reg.accomondation.doubleroom){
            var p = regService.getPersonsRegistration(reg.accomondation.roommateID);
            regService.removeRoom(p);
            regService.removeRoom(reg.person);
            var idx2 = $scope.hasRoom.indexOf(p.personID);
            $scope.hasRoom.splice(idx2, 1);
        } else regService.removeRoom(reg.person);
    };

    $scope.hasRoommate = function(person){
        return regService.hasRoommate(person);
    };

    $scope.checkIfHasRoom = function(obj){
        for (i = 0; i<$scope.hasRoom.length; i++){
            if ($scope.hasRoom[i] == obj.person.personID){
                return false;
            }
        }
        return true;
    };

    $scope.checkIfSelected = function(obj){
        if ($scope.firstPersonRoom == null || obj.person.personID == $scope.firstPersonRoom.personID) return false;
        else return true;
    };


    $scope.saveRoom = function(acc, first, second){ // Her skal date også inn.
        if ($scope.checkboxAccModel.rad){ // Dobbeltrom. Validering: first.personID !== undefined && second !== undefined (funker ikke når dobbeltrom + secondPerson ikke er valgt).
                acc.doubleroom = true;
                acc.selectedAccomondation = $scope.selectedAccomondation;
                regService.saveRoom(acc, first, second);
                $scope.hasRoom.push(first.personID);
                $scope.hasRoom.push(second.personID);
        } else { // Enkeltrom.  Validering: if (first.personID !== undefined && !$scope.checkboxAccModel.rad)
            acc.doubleroom = false;
            acc.selectedAccomondation = $scope.selectedAccomondation;
            regService.saveRoom(acc, first);
            $scope.hasRoom.push(first.personID);
            //personService.addRoommate(accomondation, first);
        }
        console.log($scope.hasRoom);
    };

    // Endre disse til å returnere true/false, likt som colorEvent() ? (Renere og mindre kode). (Legger til checkbox-value i en array, if -> true.
    $scope.wholeCourse = function wholeCourse() {
        for (var i = 0; i<$scope.dateArray; i++){
            $scope.selectedDays.push($scope.dateArray[i]);
            console.log(i + " Er checked: " + $scope.selectedDays[i]);
        }
    };

    $scope.selectDay = function(day){
        $scope.allDaysCheck = false;
        for(var i = 0; i < $scope.selectedDays.length; i++){
            if (day == $scope.selectedDays[i]){
                $scope.selectedDays.splice(i,1);
                console.log($scope.selectedDays[i] + " er fjernet. Lengden på tabell er nå " + $scope.selectedDays.length);
                return;
            }
        }
        $scope.selectedDays.push(day);
        console.log(day + " er pushet. Lengden på tabell er nå " + $scope.selectedDays.length);
        if ($scope.selectedDays.length == $scope.dateArray.length){
            $scope.allDaysCheck = true;
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

    self.inputParameterResolver = function(registration){
        var optionalPersonalia = [];
        var optionalWorkplace = [];
        var extraInfo = [];
        for (var prop in registration.optionalPersonalia){ // Prop is indexnumber for the question in course.form.optionalPersonalia
            if (registration.optionalPersonalia[prop] != undefined) {
                var i = parseInt(prop);
                var inputParameter = {parameter: registration.optionalPersonalia[prop], type: $scope.course.form.optionalPersonalia[i].type};
                optionalPersonalia.push(inputParameter);
            }
        }
        for (var prop in registration.optionalWorkplace){ // Prop is indexnumber for the question in course.form.optionalWorkplace
            if (registration.optionalWorkplace[prop] != undefined) {
                var i = parseInt(prop);
                var inputParameter = {parameter: registration.optionalWorkplace[prop], type: $scope.course.form.optionalWorkplace[i].type};
                optionalWorkplace.push(inputParameter);
            }
        }
        for (var prop in registration.extraInfo){ // Prop is indexnumber for the question in course.form.extraInfo
            if (registration.extraInfo[prop] != undefined) {
                var i = parseInt(prop);
                var inputParameter = {parameter: registration.extraInfo[prop], type: $scope.course.form.extraInfo[i].type};
                extraInfo.push(inputParameter);
            }
        }
        return {optionalPersonalia: optionalPersonalia, optionalWorkplace: optionalWorkplace, extraInfo: extraInfo};
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
        if (c.hotels != null){
            course.hotels = c.hotels;
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

    $scope.selectAccomondation = function (accomondation) {
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
            } if (notOverlaps){
                $scope.selectedSessions.push(session);
            }
        }
    };

    self.resolveID = function(sessions){
        var ids = [];
        for (var i = 0; i < sessions.length; i++){
            ids.push(sessions[i].id);
        }
        return ids;
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
    $scope.newacc.startDate = Date.UTC(2016, 6, 7);  // new Date();
    $scope.newacc.endDate = Date.UTC(2016, 6, 7);
    $scope.minDate = Date.UTC(2016, 1, 9);  // new Date();
    $scope.maxDate = Date.UTC(2016, 1, 15);
    $scope.getType = function (key) {
        return Object.prototype.toString.call($scope[key]);
    };
}]);