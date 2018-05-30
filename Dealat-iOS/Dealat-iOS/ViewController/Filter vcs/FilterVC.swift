//
//  FilterVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/18/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import DropDown
import SkyFloatingLabelTextField

class FilterVC: BaseTVC {
    
//    @IBOutlet weak var categoryLbl : UILabel!
//    @IBOutlet weak var locationLbl : UILabel!
    
    @IBOutlet var tfields: [SkyFloatingLabelTextField]!

    // general
    @IBOutlet weak var tfSearch : SkyFloatingLabelTextField!
    @IBOutlet weak var tfCategory : SkyFloatingLabelTextField!
    @IBOutlet weak var tfCity: SkyFloatingLabelTextField!
    @IBOutlet weak var tfLocation : SkyFloatingLabelTextField!
    @IBOutlet weak var tfPrice1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfPrice2 : SkyFloatingLabelTextField!
    
    // 1
    @IBOutlet weak var tfType1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfModel : SkyFloatingLabelTextField!
    @IBOutlet weak var tfTrans : SkyFloatingLabelTextField!
    @IBOutlet weak var tfYear : SkyFloatingLabelTextField!
    @IBOutlet weak var tfKilometers1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfKilometers2 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfCapacity1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfCapacity2 : SkyFloatingLabelTextField!

//    @IBOutlet weak var tfCapacity : SkyFloatingLabelTextField!

    
    // 2
    @IBOutlet weak var tfRooms_num1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfRooms_num2 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfFloors_number1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfFloors_number2 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfFloor1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfFloor2 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfSpace1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfSpace2 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfWith_furniture : SkyFloatingLabelTextField!
    @IBOutlet weak var tfPropertyName : SkyFloatingLabelTextField!


    //3
    @IBOutlet weak var tfType3 : SkyFloatingLabelTextField!
    
    //4
    @IBOutlet weak var tfType4 : SkyFloatingLabelTextField!
    
    //8
    @IBOutlet weak var tfSchedule : SkyFloatingLabelTextField!
    @IBOutlet weak var tfGender: SkyFloatingLabelTextField!
    @IBOutlet weak var tfEducation : SkyFloatingLabelTextField!
    @IBOutlet weak var tfCertificate : SkyFloatingLabelTextField!
    @IBOutlet weak var tfSalary1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfSalary2 : SkyFloatingLabelTextField!

    //11
    @IBOutlet weak var tfStatus : SkyFloatingLabelTextField!

    
    var locationDropDown = DropDown()
    var cities = [City]()

    var locations = [Location]()
    var typesBase2 = [Type]()
    var typesBase = [Type]()
    var schedules = [Schedule]()
    var educations = [Education]()
    var certificates = [Certificate]()
    var states = [PropertyState]()

    
    weak var filterBaseVC : FilterBaseVC!
    
    var filter = FilterParams(){
        didSet{
            self.refreshData()
        }
    }
    
    func ifHidden(index : IndexPath) -> Bool{
        
        if let cat = self.filter.category,cat.hidden_fields != nil {
            
            switch (index.section,index.row){
                
            case (1,0): return cat.hidden_fields.contains("type_name")
            case (1,1): return cat.hidden_fields.contains("type_model_name")
            case (1,2): return cat.hidden_fields.contains("is_automatic")
            case (1,3): return cat.hidden_fields.contains("manufacture_date")
            case (1,4): return cat.hidden_fields.contains("kilometer")
            case (1,5): return cat.hidden_fields.contains("engine_capacity")

            case (2,0): return cat.hidden_fields.contains("space")
            case (2,1): return cat.hidden_fields.contains("rooms_num")
            case (2,2): return cat.hidden_fields.contains("floors_number")
            case (2,3): return cat.hidden_fields.contains("floor")
            case (2,4): return cat.hidden_fields.contains("furniture")
                
            case (3,0): return cat.hidden_fields.contains("type_name")
                
            case (4,0): return cat.hidden_fields.contains("type_name")
                
            case (8,0): return cat.hidden_fields.contains("education")
            case (8,1): return cat.hidden_fields.contains("certificate_name")
            case (8,2): return cat.hidden_fields.contains("schedule")
            case (8,3): return cat.hidden_fields.contains("gender")
            case (8,4): return cat.hidden_fields.contains("salary")
                
            default:
                break
            }
        }
        
        return false
    }

    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.filter = Provider.filter
        
        setupViews()
        getData()
    }
    
    
    func setupViews(){
        
        self.tableView.tableFooterView = UIView()
        self.tableView.separatorStyle = .none

        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 100

        
        let imgBack = UIImageView.init(image: #imageLiteral(resourceName: "Background-blur"))
        self.tableView.backgroundView = imgBack
        
        // IQDropDownTextField
        for i in tfields{
            i.lineColor = .clear
            i.lineHeight = 0
            i.selectedLineColor = .clear
            i.selectedLineHeight = 0
            
            i.selectedTitleColor = UIColor.lightGray
            i.placeholderColor = Theme.Color.White
            i.titleColor = UIColor.lightGray
            i.textColor = .white
            
            if let place = i.placeholder{
                let arr = place.components(separatedBy: "*")
                if let temp = arr.first{
                    if arr.count == 2{
                        i.placeholder = temp.localized + "*"
                    }else{
                        i.placeholder = temp.localized
                    }
                }
            }
        }
    }
    
    func refreshData(){
        
        let allString = "All".localized
        
        if filter.city != nil{
            self.tfCity.text = filter.city.city_name
        }else{
            self.tfCity.text = allString
        }

        if filter.location != nil{
            self.tfLocation.text = filter.location.location_name
        }else{
            self.tfLocation.text = allString
        }
        
        if filter.category != nil{
            self.tfCategory.text = filter.category.category_name
            
            self.typesBase = self.typesBase2.filter({ (t) -> Bool in
                if let c = t.tamplate_id, let c2 = filter.category.tamplate_id{
                    print("CCC : \(c.intValue) -- CCC2 \(c2.intValue)")
                    return c.intValue == c2.intValue
                }
                return false
            })

        }else{
            self.tfCategory.text = allString
        }
        
            
        if filter.type_id != nil{
            self.tfType1.text = filter.type_id.full_type_name
            self.tfType3.text = filter.type_id.full_type_name
            self.tfType4.text = filter.type_id.full_type_name
        }else{
            self.tfType1.text = allString
            self.tfType3.text = allString
            self.tfType4.text = allString
        }
        
        if filter.category != nil{
            self.tfCategory.text = filter.category.category_name
        }else{
            self.tfCategory.text = allString
        }

        
        if filter.type_model_id != nil{
            self.tfModel.text = filter.type_model_id!.compactMap({$0.name}).joined(separator: ",")
        }else{
            self.tfModel.text = allString
        }
        
        if filter.is_automatic != nil{
            self.tfTrans.text = (filter.is_automatic == 0) ? "Manual".localized : "Automatic".localized
        }else{
            self.tfTrans.text = allString
        }
        
        if filter.is_new != nil{
            self.tfStatus.text = (filter.is_new == 0) ? "old".localized : "new".localized
        }else{
            self.tfStatus.text = allString
        }
        
        if filter.manufacture_date != nil{
            self.tfYear.text = filter.manufacture_date!.compactMap({$0}).joined(separator: ",")
        }else{
            self.tfYear.text = allString
        }
        
        if filter.propertyStates != nil{
            self.tfPropertyName.text = filter.propertyStates!.compactMap({$0.name}).joined(separator: ",")
        }else{
            self.tfPropertyName.text = allString
        }

        
        
//        if filter.engine_capacity != nil{
//            self.tfCapacity.text = filter.engine_capacity!.compactMap({$0}).joined(separator: ",")
//        }else{
//            self.tfCapacity.text = allString
//        }
        
        
        if filter.education_id != nil{
            self.tfEducation.text = filter.education_id!.compactMap({$0.name}).joined(separator: ",")
        }else{
            self.tfEducation.text = allString
        }
        
        if filter.certificate_id != nil{
            self.tfCertificate.text = filter.certificate_id!.compactMap({$0.name}).joined(separator: ",")
        }else{
            self.tfCertificate.text = allString
        }

        
        
        if filter.schedule_id != nil{
            self.tfSchedule.text = filter.schedule_id!.compactMap({$0.name}).joined(separator: ",")
        }else{
            self.tfSchedule.text = allString
        }

        if filter.gender != nil{
            self.tfGender.text = (filter.gender! == 1) ? "Male".localized : "Famale".localized
        }else{
            self.tfGender.text = allString
        }

        
        if filter.with_furniture != nil{
            self.tfWith_furniture.text = (filter.with_furniture == 0) ? "no".localized : "yes".localized
        }else{
            self.tfWith_furniture.text = allString
        }

        
        if filter.propertyStates != nil{
            self.tfPropertyName.text = filter.propertyStates!.compactMap({$0.name}).joined(separator: ",")
        }else{
            self.tfPropertyName.text = allString
        }


        self.filter.searchText = self.tfSearch.text
        
        self.filter.price.min = self.tfPrice1.text
        self.filter.price.max = self.tfPrice2.text
        
        self.filter.kilometer.min = self.tfKilometers1.text
        self.filter.kilometer.max = self.tfKilometers2.text

        self.filter.rooms_num.min = self.tfRooms_num1.text
        self.filter.rooms_num.max = self.tfRooms_num2.text

        self.filter.floors_number.min = self.tfFloors_number1.text
        self.filter.floors_number.max = self.tfFloors_number2.text

        
        self.filter.space.min = self.tfSpace1.text
        self.filter.space.max = self.tfSpace2.text

        self.filter.floor.min = self.tfFloor1.text
        self.filter.floor.max = self.tfFloor2.text

        self.filter.salary.min = self.tfSalary1.text
        self.filter.salary.max = self.tfSalary2.text
        
        self.filter.engine_capacity.min = self.tfCapacity1.text
        self.filter.engine_capacity.max = self.tfCapacity2.text

        self.tableView.reloadData()
    }
    
    
    override func getRefreshing() {
        Communication.shared.get_data_lists { (cities,locations, types, educations, schedules, periods,certificates,states) in
            self.hideLoading()
            
            self.cities = cities
            self.typesBase2 = types
            self.typesBase = types
            self.locations = locations
            self.educations = educations
            self.schedules = schedules
            self.certificates = certificates
            self.states = states
        }
    }
    
    
    
//    func setupLocations(){
//        var array = [String]()
//
//        for i in self.locations{
//            array.append(i.city_name + " -" + i.location_name)
//        }
//
//        locationDropDown.dataSource = array
//        locationDropDown.anchorView = self.locationLbl
//        locationDropDown.direction = .bottom
//        locationDropDown.bottomOffset = CGPoint(x: 0, y: locationLbl.bounds.height)
//
//        locationDropDown.selectionAction = { [unowned self] (index, item) in
//            self.locationLbl.text = item
//            self.filter.location = self.locations[index]
//            self.refreshData()
//        }
//    }
    
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
//        if indexPath.row == 0{
//            self.locationDropDown.show()
//        }
        
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "PopupVC") as! PopupVC
        vc.modalPresentationStyle = .overCurrentContext
        vc.modalTransitionStyle = .crossDissolve
        vc.parentVC = self
        
        switch indexPath.section {
        case 0:
            switch  indexPath.row{
            case 1:
                let vc2 = self.storyboard?.instantiateViewController(withIdentifier: "ChooseCatVC3") as! ChooseCatVC
                
                let c = Cat()
                c.category_name = "ChooseCategory".localized
                c.children = Provider.shared.cats
                
                vc2.cat = c
                vc2.filterVC = self
                vc2.modalPresentationStyle = .overCurrentContext
                vc2.modalTransitionStyle = .crossDissolve
                self.present(vc2, animated: true, completion: nil)
            case 2:
                vc.type = -1
                self.present(vc, animated: true, completion: nil)
            case 3:
                vc.type = 1
                self.present(vc, animated: true, completion: nil)
            default: break
            }
            
        case 1:
            switch indexPath.row{
            case 0:
                vc.type = 2
                self.present(vc, animated: true, completion: nil)
            case 1:
                vc.type = 3
                self.present(vc, animated: true, completion: nil)
            case 2:
                vc.type = 7
                self.present(vc, animated: true, completion: nil)
            case 3:
                vc.type = 6
                self.present(vc, animated: true, completion: nil)
//            case 5:
//                vc.type = 9
//                self.present(vc, animated: true, completion: nil)
            default: break
            }
        case 2:
            switch indexPath.row{
            case 4:
                vc.type = 12
                self.present(vc, animated: true, completion: nil)
            case 5:
                vc.type = 13
                self.present(vc, animated: true, completion: nil)

            default: break
            }
            
        case 3,4:
            vc.type = 2
            self.present(vc, animated: true, completion: nil)
            
        case 8:
            if indexPath.row == 0{
                vc.type = 5
                self.present(vc, animated: true, completion: nil)
            }else if indexPath.row == 1{
                vc.type = 11
                self.present(vc, animated: true, completion: nil)
            }
            else if indexPath.row == 2{
                vc.type = 4
                self.present(vc, animated: true, completion: nil)
            }else if indexPath.row == 3{
                vc.type = 10
                self.present(vc, animated: true, completion: nil)
            }

            
        case 11:
            vc.type = 8
            self.present(vc, animated: true, completion: nil)

            
            
        default: break
            
        }
        
        
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return nil
    }
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        let h : CGFloat = 54.0
        let h2 : CGFloat = 80
        
        
        if ifHidden(index: indexPath){
            return 0
        }
        
        // when job hide price
        if self.filter.category != nil{
            if self.self.filter.category.tamplate_id.intValue == 8{
                if indexPath == IndexPath(row: 4, section: 0){
                    return 0
                }
            }
        }
        
        switch indexPath.section {
        case 0:
            return indexPath.row != 4 ?  h : h2
            
        case 11:
            if self.filter.category != nil{
                if [1,2,3,4,5,6,7,9].contains(self.filter.category.tamplate_id.intValue){
                    return UITableViewAutomaticDimension
                }
            }
            
            return 0
        default:
            
            if indexPath.section == 2,indexPath.row == 3{
                return 0
            }
            
            if self.filter.category != nil{
                if self.filter.category.tamplate_id.intValue == indexPath.section{
                    if [(0,3),(1,4),(1,5),(2,0),(2,1),(2,2),(8,4)].contains(where: ({$0.0 == indexPath.section && $0.1 == indexPath.row})){
                        return h2
                    }else{
                        return h
                    }
                }else{
                    return 0
                }
            }else{
                return 0
            }
        }
    }
    
    
    
}
