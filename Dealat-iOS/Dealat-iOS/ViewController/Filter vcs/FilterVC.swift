//
//  FilterVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/18/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import DropDown

class FilterVC: BaseTVC {
    
    @IBOutlet weak var categoryLbl : UILabel!
    @IBOutlet weak var locationLbl : UILabel!
    
    var locationDropDown = DropDown()
    var locations = [Location]()
    
    weak var filterBaseVC : FilterBaseVC!
    
    
    var searchText : String!
    var selectedCategory : Cat!{
        didSet{
            self.refreshData()
        }
    }
    var selectedLocation : Location!{
        didSet{
            self.refreshData()
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.searchText = Provider.searchText
        self.selectedCategory = Provider.selectedCategory
        self.selectedLocation = Provider.selectedLocation
        
        getData()
        self.tableView.tableFooterView = UIView()
        
    }
    
    func refreshData(){
        
        if  locationLbl != nil{
            if selectedLocation != nil{
                self.locationLbl.text = selectedLocation.location_name
            }else{
                self.locationLbl.text = "-"
            }
        }
        if  categoryLbl != nil{
            if selectedCategory != nil{
                self.categoryLbl.text = selectedCategory.category_name
            }else{
                self.categoryLbl.text = "-"
            }
        }
        
    }
    
    override func getRefreshing() {
        Communication.shared.get_data_lists { (locations, types, educations, schedules) in
            self.hideLoading()
            self.locations = locations
            self.setupLocations()
        }
    }
    
    
    func setupLocations(){
        var array = [String]()
        
        for i in self.locations{
            array.append(i.city_name + " -" + i.location_name)
        }
        
        //        self.locationLbl.text = "-"
        
        //        if self.locations.count > 0 {
        //            self.locationLbl.text = self.cities.first!.name
        //            self.selectedLocation = self.locations.first!
        //        }
        //
        locationDropDown.dataSource = array
        locationDropDown.anchorView = self.locationLbl
        locationDropDown.direction = .bottom
        locationDropDown.bottomOffset = CGPoint(x: 0, y: locationLbl.bounds.height)
        
        locationDropDown.selectionAction = { [unowned self] (index, item) in
            self.locationLbl.text = item
            self.selectedLocation = self.locations[index]
        }
    }
    
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.row == 0{
            self.locationDropDown.show()
        }
        
        if indexPath.row == 1{
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
    
}
