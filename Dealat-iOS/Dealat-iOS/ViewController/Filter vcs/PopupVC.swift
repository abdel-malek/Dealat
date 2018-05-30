//
//  PopupVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 3/18/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class PopupVC: BaseVC, UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet weak var tableView : UITableView!
    @IBOutlet weak var vv : UIView!
    
    var multi : Bool = false
    var parentVC: FilterVC!
    var type = 0
    
    /*
     1 -> locations
     2 -> typesBase
     3 -> models
     4 -> schedules
     5 -> educations
     6 -> manufacture_date
     7 -> transmission
     8 -> status
     9 -> engine_capacity
     10 -> genders
     11 -> certificates
     12 -> with_furniture
     13 -> property state

     */
    
    var years : [String] {
        var arr = [String]()
        for i in 1970...2018{
            arr.append("\(i)")
        }
        return arr
    }
    
    var capacities : [String] {
        var arr = [String]()
        for i in stride(from: 1100, to: 5400, by: 100) {
            arr.append("\(i)")
        }
        return arr
    }

    var transmission : [String] {
        return ["Manual".localized,"Automatic".localized]
    }
    var status : [String] {
        return ["old".localized,"new".localized]
    }
    
    var genders : [String] {
        return ["Male".localized,"Famale".localized]
    }

//    var yesno : [String] {
//        return ["yes".localized,"no".localized]
//    }

    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
    }
    
    override func setupViews() {
        
        self.tableView.delegate = self
        self.tableView.dataSource = self
        self.tableView.tableFooterView = UIView()
        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 100
        
        self.vv.addGestureRecognizer(UITapGestureRecognizer.init(target: self, action: #selector(self.dis)))
    }
    
    @objc @IBAction func dis(){
        self.dismiss(animated: true, completion: nil)
    }
    
    
    func reloadData(){
        self.tableView.reloadData()
        self.parentVC.refreshData()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        var cnt = 0
        switch type {
        case -1:
            cnt = parentVC.cities.count
        case 1:
            if let c = parentVC.filter.city{
                cnt = c.locations.count
            }
        case 2:
            cnt = parentVC.typesBase.count
        case 3:
            if self.parentVC.filter.type_id != nil{
                if let type = parentVC.typesBase.filter({$0.type_id == self.parentVC.filter.type_id.type_id}).first{
                    cnt = type.models.count
                }
            }
        case 4:
            cnt = parentVC.schedules.count
        case 5:
            cnt = parentVC.educations.count
        case 6:
            cnt = years.count
        case 7:
            cnt = transmission.count
        case 8:
            cnt = status.count
//        case 9:
//            cnt = capacities.count
        case 10:
            cnt = genders.count
        case 11:
            cnt = parentVC.certificates.count
        case 12:
            cnt = 2
        case 13:
            cnt = parentVC.states.count

        default:
            cnt = 0
        }
        
        return cnt + 1
    }
    
    
    
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        
        if indexPath.row == 0{
            switch type {
            case -1 :
                cell.accessoryType = self.parentVC.filter.city == nil ? .checkmark : .none
            case 1:
                cell.accessoryType = self.parentVC.filter.location == nil ? .checkmark : .none
            case 2:
                cell.accessoryType = self.parentVC.filter.type_id == nil ? .checkmark : .none
            case 3:
                cell.accessoryType = self.parentVC.filter.type_model_id == nil ? .checkmark : .none
            case 4:
                cell.accessoryType = self.parentVC.filter.schedule_id == nil ? .checkmark : .none
            case 5:
                cell.accessoryType = self.parentVC.filter.education_id == nil ? .checkmark : .none
            case 6:
                cell.accessoryType = self.parentVC.filter.manufacture_date == nil ? .checkmark : .none
            case 7:
                cell.accessoryType = self.parentVC.filter.is_automatic == nil ? .checkmark : .none
            case 8:
                cell.accessoryType = self.parentVC.filter.is_new == nil ? .checkmark : .none
//            case 9:
//                cell.accessoryType = self.parentVC.filter.engine_capacity. == nil ? .checkmark : .none
            case 10:
                cell.accessoryType = self.parentVC.filter.gender == nil ? .checkmark : .none
            case 11:
                cell.accessoryType = self.parentVC.filter.certificate_id == nil ? .checkmark : .none
            case 12:
                cell.accessoryType = self.parentVC.filter.with_furniture == nil ? .checkmark : .none
            case 13:
                cell.accessoryType = self.parentVC.filter.propertyStates == nil ? .checkmark : .none
            default:
                break
            }
        }else{
            let index = indexPath.row - 1
            cell.accessoryType = .none

            switch type {
            case -1:
                if let l = self.parentVC.filter.city{
                        if l.city_id == self.parentVC.cities[index].city_id{
                            cell.accessoryType = .checkmark
                        }else{
                            cell.accessoryType = .none
                        }
                }
            case 1:
                if let l = self.parentVC.filter.location{
                    if let c = self.parentVC.filter.city{
                        if l.location_id == c.locations[index].location_id{
                            cell.accessoryType = .checkmark
                        }else{
                            cell.accessoryType = .none
                        }
                    }
                }
            case 2:
                if let l = self.parentVC.filter.type_id{
                    if l.type_id == self.parentVC.typesBase[index].type_id{
                        cell.accessoryType = .checkmark
                    }else{
                        cell.accessoryType = .none
                    }
                }
                
            case 3:
                if let type = parentVC.typesBase.filter({$0.type_id == self.parentVC.filter.type_id.type_id}).first{
                    if self.parentVC.filter.type_model_id != nil,self.parentVC.filter.type_model_id!.count > 0{
                        
                        for i in self.parentVC.filter.type_model_id!{
                            if i.type_model_id == type.models[index].type_model_id{
                                cell.accessoryType = .checkmark
                            }
//                            else{
//                                cell.accessoryType = .none
//                            }
                        }
                    }
                }
            case 4:
                if let schedules = self.parentVC.filter.schedule_id{
                    for i in schedules{
                        if i.schedule_id == self.parentVC.schedules[index].schedule_id{
                            cell.accessoryType = .checkmark
                        }
//                        else{
//                            cell.accessoryType = .none
//                        }
                    }
                }
            case 5:
                if let educations = self.parentVC.filter.education_id{
                    for i in educations{
                        if i.education_id == self.parentVC.educations[index].education_id{
                            cell.accessoryType = .checkmark
                        }
//                        else{
//                            cell.accessoryType = .none
//                        }
                    }
                }
            case 6:
                if let years = self.parentVC.filter.manufacture_date{
                    for i in years{
                        if i == self.years[index]{
                            cell.accessoryType = .checkmark
                        }
//                        else{
//                            cell.accessoryType = .none
//                        }
                    }
                }
            case 7:
                if let is_automatic = self.parentVC.filter.is_automatic{
                        if is_automatic == self.transmission.index(where: {$0 == self.transmission[index]}){
                            cell.accessoryType = .checkmark
                        }else{
                            cell.accessoryType = .none
                        }
                }
            case 8:
                if let is_new = self.parentVC.filter.is_new{
                    if is_new == self.status.index(where: {$0 == self.status[index]}){
                        cell.accessoryType = .checkmark
                    }else{
                        cell.accessoryType = .none
                    }
                }
//            case 9:
//                if let capacities = self.parentVC.filter.engine_capacity{
//                    for i in capacities{
//                        if i == self.capacities[index]{
//                            cell.accessoryType = .checkmark
//                        }
//                    }
//                }
            case 10:
                if let gender = self.parentVC.filter.gender{
                    for i in genders{
                        if i == self.genders[gender - 1]{
                            cell.accessoryType = .checkmark
                        }
                    }
                }
            case 11:
                if let certificates = self.parentVC.filter.certificate_id{
                    for i in certificates{
                        if i.certificate_id.intValue == self.parentVC.certificates[index].certificate_id.intValue{
                            cell.accessoryType = .checkmark
                        }
                    }
                }
            case 12:
                if let f = self.parentVC.filter.with_furniture{
                    if f == index{
                        cell.accessoryType = .checkmark
                    }
                }
                
            case 13:
                if let states = self.parentVC.filter.propertyStates{
                    for i in states{
                        if i.property_state_id == self.parentVC.states[index].property_state_id{
                            cell.accessoryType = .checkmark
                        }
                        //                        else{
                        //                            cell.accessoryType = .none
                        //                        }
                    }
                }

                
            default: break
            }
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        
        if indexPath.row == 0{
            cell.textLabel?.text = "All".localized
        }
        else{
            let index = indexPath.row - 1
            
            switch type {
            case -1:
                cell.textLabel?.text = self.parentVC.cities[index].city_name
            case 1:
                if let c = self.parentVC.filter.city{
                    cell.textLabel?.text = c.locations[index].location_name
                }
            case 2:
                cell.textLabel?.text = self.parentVC.typesBase[index].full_type_name
            case 3:
                if let type = parentVC.typesBase.filter({$0.type_id == self.parentVC.filter.type_id.type_id}).first{
                    cell.textLabel?.text =  type.models[index].name
                }else{
                    cell.textLabel?.text =  nil
                }
            case 4:
                cell.textLabel?.text = self.parentVC.schedules[index].name
            case 5:
                cell.textLabel?.text = self.parentVC.educations[index].name
            case 6:
                cell.textLabel?.text = self.years[index]
            case 7:
                cell.textLabel?.text = self.transmission[index]
            case 8:
                cell.textLabel?.text = self.status[index]
//            case 9:
//                cell.textLabel?.text = self.capacities[index]
            case 10:
                cell.textLabel?.text = self.genders[index]
            case 11:
                cell.textLabel?.text = self.parentVC.certificates[index].name
            case 12:
                cell.textLabel?.text = (index == 0) ? "no".localized : "yes".localized
            case 13:
                cell.textLabel?.text = self.parentVC.states[index].name
                
            default:
                break
            }
            
        }
        return cell
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        if indexPath.row == 0{
            
            switch type {
            case -1:
                self.parentVC.filter.location = nil
                self.parentVC.filter.city = nil
            case 1:
                self.parentVC.filter.location = nil
            case 2:
                self.parentVC.filter.type_id = nil
            case 3:
                self.parentVC.filter.type_model_id = nil
            case 4:
                self.parentVC.filter.schedule_id = nil
            case 5:
                self.parentVC.filter.education_id = nil
            case 6:
                self.parentVC.filter.manufacture_date = nil
            case 7:
                self.parentVC.filter.is_automatic = nil
            case 8:
                self.parentVC.filter.is_new = nil
//            case 9:
//                self.parentVC.filter.engine_capacity = nil
            case 10:
                self.parentVC.filter.gender = nil
            case 11:
                self.parentVC.filter.certificate_id = nil
            case 12:
                self.parentVC.filter.with_furniture = nil
            case 13:
                self.parentVC.filter.propertyStates = nil

            default:
                break
            }
            
            self.reloadData()

        }else{
            
            let index = indexPath.row - 1
            
            switch type {
            case -1:
                self.parentVC.filter.location = nil
                self.parentVC.filter.city = self.parentVC.cities[index]
            case 1:
                if let c = self.parentVC.filter.city{
                    self.parentVC.filter.location = c.locations[index]
                }
            case 2:
                self.parentVC.filter.type_id = self.parentVC.typesBase[index]
                self.parentVC.filter.type_model_id = nil
            case 3:
                if let type = parentVC.typesBase.filter({$0.type_id == self.parentVC.filter.type_id.type_id}).first{
                    
                    var arr = self.parentVC.filter.type_model_id
                    var deleted = false
                    if arr == nil{
                        arr = [Model]()
                    }
                    
                    // delete if exist
                    for i in 0..<arr!.count{
                        if arr![i].type_model_id == type.models[index].type_model_id{
                            deleted = true
                            arr?.remove(at: i)
                            break
                        }
                    }
                    
                    // add if new
                    if !deleted{
                        arr?.append(type.models[index])
                    }
                    
                    // final
                    self.parentVC.filter.type_model_id = arr!.isEmpty ? nil : arr!
                }
                
            case 4:
                var arr = self.parentVC.filter.schedule_id
                var deleted = false
                if arr == nil{
                    arr = [Schedule]()
                }
                
                // delete if exist
                for i in 0..<arr!.count{
                    if arr![i].schedule_id == self.parentVC.schedules[index].schedule_id{
                        deleted = true
                        arr?.remove(at: i)
                        break
                    }
                }
                
                // add if new
                if !deleted{
                    arr?.append(self.parentVC.schedules[index])
                }
               
                print("SDFSDF \(arr)")

                
                
                // final
                self.parentVC.filter.schedule_id = arr!.isEmpty ? nil : arr!
                
            case 5:
                var arr = self.parentVC.filter.education_id
                var deleted = false
                if arr == nil{
                    arr = [Education]()
                }
                
                // delete if exist
                for i in 0..<arr!.count{
                    if arr![i].education_id == self.parentVC.educations[index].education_id{
                        deleted = true
                        arr?.remove(at: i)
                        break
                    }
                }
                
                // add if new
                if !deleted{
                    arr?.append(self.parentVC.educations[index])
                }
                
                // final
                self.parentVC.filter.education_id = arr!.isEmpty ? nil : arr!
            case 6:
                var arr = self.parentVC.filter.manufacture_date
                var deleted = false
                if arr == nil{
                    arr = [String]()
                }
                
                // delete if exist
                for i in 0..<arr!.count{
                    if arr![i] == self.years[index]{
                        deleted = true
                        arr?.remove(at: i)
                        break
                    }
                }
                
                // add if new
                if !deleted{
                    arr?.append(self.years[index])
                }
                
                // final
                self.parentVC.filter.manufacture_date = arr!.isEmpty ? nil : arr!
                
            case 7:
                self.parentVC.filter.is_automatic = index
            case 8:
                self.parentVC.filter.is_new = index
            /*case 9:
                var arr = self.parentVC.filter.engine_capacity
                var deleted = false
                if arr == nil{
                    arr = [String]()
                }
                
                // delete if exist
                for i in 0..<arr!.count{
                    if arr![i] == self.capacities[index]{
                        deleted = true
                        arr?.remove(at: i)
                        break
                    }
                }
                
                // add if new
                if !deleted{
                    arr?.append(self.capacities[index])
                }
                
                // final
                self.parentVC.filter.engine_capacity = arr!.isEmpty ? nil : arr!*/
            case 10:
                self.parentVC.filter.gender = index + 1
            case 11:
                var arr = self.parentVC.filter.certificate_id
                var deleted = false
                if arr == nil{
                    arr = [Certificate]()
                }
                
                // delete if exist
                for i in 0..<arr!.count{
                    if arr![i].certificate_id == self.parentVC.certificates[index].certificate_id{
                        deleted = true
                        arr?.remove(at: i)
                        break
                    }
                }
                
                // add if new
                if !deleted{
                    arr?.append(self.parentVC.certificates[index])
                }
                
                // final
                self.parentVC.filter.certificate_id = arr!.isEmpty ? nil : arr!
            case 12:
                self.parentVC.filter.with_furniture = index

            case 13:
                var arr = self.parentVC.filter.propertyStates
                var deleted = false
                if arr == nil{
                    arr = [PropertyState]()
                }
                
                // delete if exist
                for i in 0..<arr!.count{
                    if arr![i].property_state_id == self.parentVC.states[index].property_state_id{
                        deleted = true
                        arr?.remove(at: i)
                        break
                    }
                }
                
                // add if new
                if !deleted{
                    arr?.append(self.parentVC.states[index])
                }
                
                // final
                self.parentVC.filter.propertyStates = arr!.isEmpty ? nil : arr!


            default:
                break
            }
            
            
            self.reloadData()
            
        }
    }
    
    
    
}
