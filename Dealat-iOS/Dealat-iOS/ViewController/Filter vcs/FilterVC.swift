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
    
    // 2
    @IBOutlet weak var tfRooms_num1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfRooms_num2 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfFloor1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfFloor2 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfSpace1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfSpace2 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfWith_furniture : SkyFloatingLabelTextField!

    //3
    @IBOutlet weak var tfType3 : SkyFloatingLabelTextField!
    
    //4
    @IBOutlet weak var tfType4 : SkyFloatingLabelTextField!
    
    //8
    @IBOutlet weak var tfSchedule : SkyFloatingLabelTextField!
    @IBOutlet weak var tfEducation : SkyFloatingLabelTextField!
    @IBOutlet weak var tfSalary1 : SkyFloatingLabelTextField!
    @IBOutlet weak var tfSalary2 : SkyFloatingLabelTextField!

    
    var locationDropDown = DropDown()
    var locations = [Location]()
    
    weak var filterBaseVC : FilterBaseVC!
    
    var filter = FilterParams(){
        didSet{
            self.refreshData()
        }
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
                    i.placeholder = temp.localized + "*"
                }
            }
        }
    }
    
    func refreshData(){
        
        if filter.location != nil{
            self.tfLocation.text = filter.location.location_name
        }else{
            self.tfLocation.text = "-"
        }
        
        if filter.category != nil{
            self.tfCategory.text = filter.category.category_name
        }else{
            self.tfCategory.text = "-"
        }
    }
    
    override func getRefreshing() {
        Communication.shared.get_data_lists { (locations, types, educations, schedules) in
            self.hideLoading()
            
            self.typesBase = types
            self.locations = locations
            self.educations = educations
            self.schedules = schedules
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
        
        if indexPath.section == 0, indexPath.row == 1{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "ChooseCatVC2") as! ChooseCatVC
            
            let c = Cat()
            c.category_name = "ChooseCategory".localized
            c.children = Provider.shared.cats
            
            vc.cat = c
            vc.filterVC = self
            vc.modalPresentationStyle = .overCurrentContext
            vc.modalTransitionStyle = .crossDissolve
            self.present(vc, animated: true, completion: nil)
        }
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return nil
    }
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        let h : CGFloat = 54.0
        
        switch indexPath.section {
        case 0:
            return h
        case 11:
            if [1,2,3,4,5,6,7,9].contains(indexPath.section){
                return UITableViewAutomaticDimension
            }else{
                return 0
            }
        default:
            if self.filter.category != nil{
                return (self.filter.category.tamplate_id.intValue == indexPath.section) ? h : 0
            }else{
                return 0
            }
        }
    }
    
}
