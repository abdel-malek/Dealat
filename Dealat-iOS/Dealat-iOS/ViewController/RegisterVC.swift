//
//  RegisterVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/14/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class RegisterVC: BaseVC {
    
    //    @IBOutlet weak var tfEmail : UITextField!
    //    @IBOutlet weak var tfPassword : UITextField!
    //    @IBOutlet weak var tfConfirmPassword : UITextField!
    
    
    @IBOutlet weak var tfName : UITextField!
    @IBOutlet weak var tfPhone: UITextField!
    
    /*@IBOutlet weak var tfLocation : UITextField!
    
    var locations = [Location]()
    var locationPicker : UIPickerView!
    var selectedLocation : Location!{
        didSet{
            if let loc = selectedLocation{
                self.tfLocation.text = loc.getLocName()
            }else{
                self.tfLocation.text = nil
            }
        }
    }*/

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        self.navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.getData()
    }
    
    override func setupViews() {
        
        self.tfName.placeholder = self.tfName.placeholder! + "*"
        self.tfPhone.placeholder = self.tfPhone.placeholder! + "*"
//        self.tfLocation.placeholder = self.tfLocation.placeholder! + "*"
        
        self.tfName.placeHolderColor = Theme.Color.White
        self.tfPhone.placeHolderColor = Theme.Color.White
//        self.tfLocation.placeHolderColor = Theme.Color.White
//
//        setPickerViewOn(tfLocation)
    }
    
    /*func setPickerViewOn(_ textField : UITextField){
        locationPicker = UIPickerView(frame:CGRect(x: 0, y: 0, width: self.view.frame.size.width, height: 216))
        locationPicker.delegate = self
        locationPicker.dataSource = self
        
        let c = UIColor.init(red: 215/255, green: 217/255, blue: 223/255, alpha: 1)
        locationPicker.setValue(c, forKey: "backgroundColor")
        textField.inputView = locationPicker
    }
    
    override func getRefreshing() {
        Communication.shared.get_data_lists { (locations, _, _, _) in
            self.hideLoading()
            self.locations = locations
            self.locationPicker.reloadAllComponents()
        }
    }
    */
    
    @IBAction func registerAction(){
     
        let name = tfName.text!
        let phone = tfPhone.text!
        
        if name.isEmpty{
            self.showErrorMessage(text: "Please enter name")
        }else if phone.isEmpty {
            self.showErrorMessage(text: "Please enter valid phone number")
        }else{
            
            self.showLoading()
            Communication.shared.users_register(phone: Provider.getEnglishNumber(phone), name: name, location_id: Provider.getCity(), callback: { (res) in
                self.hideLoading()
                
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "VerificationVC") as! VerificationVC
                self.navigationController?.pushViewController(vc, animated: true)
                
            })
            
        }
        
    }
    
    @IBAction func skipAction(){
        Provider.goToHome()
    }

    
    
}

/*extension RegisterVC : UIPickerViewDelegate, UIPickerViewDataSource{
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        
        return self.locations.count + 1
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        if row == 0{
            return "select one"
        }
        
        let loc = self.locations[row - 1]
        return loc.location_name
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
        if row == 0{
            self.selectedLocation = nil
            return
        }
        self.selectedLocation = self.locations[row - 1]
    }
    
    func pickerView(_ pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
        if row == 0{
            return NSAttributedString.init(string: "select one", attributes: [NSAttributedStringKey.foregroundColor : UIColor.lightGray])
        }
        return nil
    }
    
}

*/
