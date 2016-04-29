app.controller('AddRegCtrl', ['$scope', 'personService', 'regService',  function ($scope, personService, regService) {
    $scope.tabs = [
        { id: '1', title:'Enkeltpåmelding', content:'resources/jsp/singleReg.jsp' },
        { id: '2', title:'Gruppepåmelding', content:'resources/jsp/groupReg.jsp'}
    ];
    $scope.tabs.activeTab = 'Gruppepåmelding';
    $scope.registration = {};
    $scope.numberOfPersons = 0;
    $scope.registrations = regService.get();
    $scope.$on('regSet', function(event, data){
        $scope.registrations = data;
    });

    $scope.selectedEvents = [];
    $scope.selectedSessions = [];
    $scope.selectedDays = [];
    $scope.courses = [];
    $scope.course = {};
    $scope.$on('courseSet', function(event, data){
        $scope.course = data;
    });
    $scope.dateArray = [];
    $scope.$on('dateSet', function(event, data){
        $scope.dateArray = data;
        console.log($scope.dateArray.length + " = dateArray.length");
        console.log($scope.dateArray);
    });
    $scope.persons = regService.getPersons();
    $scope.$on('personSet', function(event, data){
        $scope.persons = data;
    });
    $scope.person = [];
    $scope.attendDate = [];
    $scope.tooltip = {title: 'Fjern romkamerat'};
    $scope.firstPersonRoom = {};
    $scope.secondPersonRoom = {};
    $scope.hasRoom = regService.getHasRoom();
    $scope.$on('hasRoomSet', function(event, data){
        $scope.hasRoom = data;
    });

    $scope.allDaysCheck = {};
    $scope.newacc = {};
    $scope.loading = true;

    $scope.saveGroupRegistration = function(){ // Må sende med course.id, course.form, session, workplace, person, pris.
        for (var i = 0; i<$scope.registrations.length; i++){
            $scope.registrations[i].optionalWorkplace = $scope.registration.optionalWorkplace;
            $scope.registrations[i].extraInfo = $scope.registration.extraInfo;
            console.log($scope.registrations[i]);
            var optionals = self.inputParameterResolver($scope.registrations[i]);
            $scope.registrations[i].optionalPersonalia = optionals.optionalPersonalia;
            $scope.registrations[i].optionalWorkplace = optionals.optionalWorkplace;
            $scope.registrations[i].extraInfo = optionals.extraInfo;
            console.log($scope.registrations[i]);
            $scope.registrations[i].registrationID = -1;
            $scope.registrations[i].course = $scope.course;
            $scope.registrations[i].sessionsToAttend = self.getIdOfArray($scope.selectedSessions);
            $scope.registrations[i].eventsToAttend = self.getIdOfArray($scope.selectedEvents);
            $scope.registrations[i].cost = self.findPrice($scope.selectedDays.length, $scope.allDaysCheck);//
            $scope.registrations[i].dates = $scope.selectedDays;


            //$scope.registrations[i].optionalPersonalia = $scope.getOptional($scope.course.form.optionalWorkplace);

            $scope.registrations[i].alternativeInvoiceAddress = $scope.registration.alternativeInvoiceAddress;
            $scope.registrations[i].workplace = $scope.registration.workplace;
            if ($scope.registrations[i].accomondation !== undefined){
                $scope.registrations[i].accomondation.roommate = $scope.getPersonName($scope.registrations[i].accomondation.roommateID);
                delete $scope.registrations[i].accomondation.roommateID;
            }
            console.log($scope.registrations[i].person);
            delete $scope.registrations[i].person.role;
            delete $scope.registrations[i].person.opt;
            console.log($scope.registrations[i].person);
        }
        regService.sendRegistrations($scope.registrations);
    };


    $scope.saveSingleRegistration = function(registration){ // Må sende med course.id, course.form, session, workplace, person, pris.
        registration.registrationID = -1;
        var optionals = self.inputParameterResolver(registration);
        registration.optionalPersonalia = optionals.optionalPersonalia;
        registration.optionalWorkplace = optionals.optionalWorkplace;
        registration.extraInfo = optionals.extraInfo;
        registration.cost = self.findPrice($scope.selectedDays.length, $scope.allDaysCheck);
        registration.sessionsToAttend = self.getIdOfArray($scope.selectedSessions);
        registration.eventsToAttend = self.getIdOfArray($scope.selectedEvents);
        registration.course = $scope.course;
        registration.dates = $scope.selectedDays;
        if ($scope.checkboxAccModel.c1 == true){
            registration.accomondation.hotelID = $scope.selectedAccomondation.id;
            if (registration.accomondation.doubleroom == false){
                delete registration.accomondation.roommate;
            }
        } else {
            delete registration.accomondation;
        }
        regService.sendRegistration(registration);
    };

    /*      BRUKES NOEN AV DISSE FUNKSJONENE?? ISÅFALL, VARSLE LARS. OM IKKE SLETTES DE TIL SLUTT.

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


    */

    self.getIdOfArray = function(array){
        var arr = [];
        for (var i = 0; i<array.length; i++){
            arr[i] = array[i].id;
        }
        return arr;
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
            price.push({amount: $scope.course.courseSingleDayFee*ant, description: 'Kursavgift Dag'});
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

    $scope.update = function (persons) { // Sender med et registrations-objekt som inneholder en person hver.
        regService.saveReg(persons, $scope.numberOfPersons);
    };

    $scope.repeat = function (number) {
        var times = [];
        for (var i = 0; i < number; i++) {
            times.push(i)
        }
        return times;
    };

    $scope.removePerson = function(reg){
        for (var i = 0; i<$scope.registrations.length; i++) {
            if ($scope.registrations[i].person === reg.person) {
                regService.deleteRegistration(reg);
            }
        }
    };

    $scope.getPerson = function(id){
        return personService.getPerson(id);
    };

    $scope.getPersonName = function(id){
        return regService.getPersonName(id);
    };

    $scope.removeRoom = function(reg){
        if (reg.accomondation.doubleroom){
            var p = regService.getPersonsRegistration(reg.accomondation.roommateID);
            regService.removeRoom(p);
            regService.removeRoom(reg.person);
        } else regService.removeRoom(reg.person);
    };

    $scope.hasRoommate = function(person){
        return regService.hasRoommate(person);
    };

    $scope.checkIfHasRoom = function(obj){
        for (var i = 0; i<$scope.hasRoom.length; i++){
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
        if ($scope.checkboxAccModel.rad){ // doubleroom. Validering: first.personID !== undefined && second !== undefined (funker ikke når dobbeltrom + secondPerson ikke er valgt).
            acc.doubleroom = true;
            acc.selectedAccomondation = $scope.selectedAccomondation;
            regService.saveRoom(acc, first, second);
        } else { // single.  Validering: if (first.personID !== undefined && !$scope.checkboxAccModel.rad)
            acc.doubleroom = false;
            acc.selectedAccomondation = $scope.selectedAccomondation;
            regService.saveRoom(acc, first);
        }
    };

    $scope.wholeCourse = function() {
        if($scope.allDaysCheck == true){
            $scope.selectedDays = [];
            for (var i = 0; i<$scope.dateArray.length; i++){
                $scope.selectedDays.push($scope.dateArray[i]);
            }
        } else{
            $scope.selectedDays = [];
        }
    };

    $scope.selectDay = function(day){
        $scope.allDaysCheck = false;
        for(var i = 0; i < $scope.selectedDays.length; i++){
            if (day == $scope.selectedDays[i]){
                $scope.selectedDays.splice(i,1);
                return;
            }
        }
        $scope.selectedDays.push(day);
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
        regService.getCourse(id).then(function(response) {
            regService.checkParticipantStatus(response.id).then(function(maps){ //prop = sessionID & map[prop] = numberOfRegsitrations. Javascript doesn't support hashmap
                var newSessions = self.setSessionStatus(response.sessions, maps[0]);
                var newEvents = self.setEventStatus(response.events, maps[1]);
                response.sessions = newSessions;
                response.events = newEvents;
                console.log(response);
                $scope.course = self.setCourse(response);
                var currentDate = $scope.course.startDate;
                if ($scope.course.startDate == $scope.course.endDate) {
                    $scope.dateArray.push(new Date(currentDate));
                } else {
                    while (currentDate <= $scope.course.endDate) {
                        $scope.dateArray.push(new Date(currentDate));
                        currentDate = $scope.addDays(currentDate, 1)
                    }
                }
                regService.setCourse($scope.course, $scope.course.roles, $scope.dateArray);
                $scope.loading = false;
            }, function(error){
                console.log("Error in checkIfParticipantStatusAreFull");
            });
        }, function(error){
            console.log("Error in getCourseById");
        });
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

    $scope.whichType = function(type){ // KAN DENNE ERSTATTES I JSP-FILEN MED ng-if="opt.type == "Input" ????
        if (type == "Input") { // Returnerer true hvis "type" er input, false hvis "checkbox".
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
        console.log(accomondation);
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

    $scope.selectEvent = function selectEvent(event) {
        var idx = $scope.selectedEvents.indexOf(event);
        if (idx > -1) $scope.selectedEvents.splice(idx, 1); // Gets unchecked
        else $scope.selectedEvents.push(event); // Gets checked.
    };

    // OK Ser etter om to datoer/tidspunk overlapper hverandre. Brukes i sesjons-valget.
    $scope.overlaps = function(startA, endA, startB, endB) { // Hvis overlapper, return true. else false.
        if (startA <= startB && startB <= endA) return true; // b starts in a
        if (startA <= endB   && endB   <= endA) return true; // b ends in a
        if (startB <  startA && endA   <  endB) return true; // a in b
        return false;
    };

    //Fjernes.
    $scope.selectedDate = new Date();
    $scope.newacc.fromDate = Date.UTC(2016, 6, 7);  // new Date();
    $scope.newacc.toDate = Date.UTC(2016, 6, 8);
    $scope.minDate = Date.UTC(2016, 6, 7);  // new Date();
    $scope.maxDate = Date.UTC(2016, 6, 8);
    $scope.getType = function (key) {
        return Object.prototype.toString.call($scope[key]);
    };

    self.setSessionStatus = function(oldSessions, map){
        var sessions = oldSessions;
        for(prop in map){
            for (var i = 0; i < sessions.length; i++){
                if(prop == sessions[i].id){
                    if(map[prop] >= sessions[i].maxnumber){
                        sessions[i].isFull = true;
                    } else{
                        sessions[i].isFull = false;
                    }
                }
            }
        }
        return sessions;
    };

    self.setEventStatus = function(oldEvents, map){
        var events = oldEvents;
        for(prop in map){
            for (var i = 0; i < events.length; i++){
                if(prop == events[i].id){
                    if(map[prop] >= events[i].maxNumber){
                        events[i].isFull = true;
                    } else{
                        events[i].isFull = false;
                    }
                }
            }
        }
        return events;
    };

    var cid = sessionStorage.cid;
    if (cid){
        regService.getSessionStorageID(cid).then(function(successCallback){
            self.getCourseById(successCallback);
        }, function(errorCallback){
            console.log("Something is wrong");
        });
    } else{
        $scope.loading = false;
        console.log("Wrong cid");
    }

}]);