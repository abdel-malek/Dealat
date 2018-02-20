//
//  ChooseCatVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/6/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit

class ChooseCatVC: BaseVC {
    
    @IBOutlet weak var vv: UIView!
    weak var homeVC : HomeVC!

    weak var filterVC : FilterVC?
    weak var newAdd : NewAddVC?


    
    var cat = Cat()
    
    private var embeddedViewController: CategoriesVC!
    private var embeddedViewController2: CategoriesVC2!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        vv.addGestureRecognizer(UITapGestureRecognizer.init(target: self, action: #selector(self.dissmiss)))
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if segue.identifier == "choose1"{
            if let nv = segue.destination as? UINavigationController{
                for i in nv.viewControllers{
                    if i.isKind(of: CategoriesVC.self){
                        self.embeddedViewController = i as! CategoriesVC
                        self.embeddedViewController.cat = self.cat
                        self.embeddedViewController.chooseCatVC = self
                    }
                }
            }
        }
        
        if segue.identifier == "choose2"{
            if let nv = segue.destination as? UINavigationController{
                for i in nv.viewControllers{
                    if i.isKind(of: CategoriesVC2.self){
                        self.embeddedViewController2 = i as! CategoriesVC2
                        self.embeddedViewController2.cat = self.cat
                        self.embeddedViewController2.chooseCatVC = self
                    }
                }
            }
        }
    }
    
    
}

    /*@IBOutlet weak var tableView : UITableView!
    @IBOutlet weak var tableHeight : NSLayoutConstraint!
    @IBOutlet weak var vv : UIView!

    var parentVC: HomeVC?
    var catBase : Cat!
    var cats : [Cat] = [Cat]()
    var isBrowse : Bool = true
 
    override func viewDidLoad() {
        super.viewDidLoad()
 
 
        self.tableView.reloadData()
 
        var c = 0
        for  i in self.cats{
            c += i.children.count
        }
 
        self.tableHeight.constant = isBrowse ? CGFloat((cats.count + 1)) * 50 : CGFloat((cats.count + c) * 50)
 
        vv.addGestureRecognizer(UITapGestureRecognizer.init(target: self, action: #selector(self.dissmiss)))
    }
 
    override func setupViews() {
        self.tableView.delegate = self
        self.tableView.dataSource = self
    }
 
 
}


extension ChooseCatVC : UITableViewDelegate, UITableViewDataSource{
 
    func numberOfSections(in tableView: UITableView) -> Int {
        if isBrowse{
            return 1
        }else{
            return cats.count
        }
    }
 
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if isBrowse{
            return cats.count + 1
        }else{
            return cats[section].children.count
        }
    }
 
 
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! CategoryCell
 
        if isBrowse{
            let c = Cat()
            c.category_name = "All"
            cell.cat = (indexPath.row == 0) ? c : cats[indexPath.row - 1]
        }else{
            cell.cat = self.cats[indexPath.section].children[indexPath.row]
 
//            if self.cats[indexPath.section].children[indexPath.row].children.isEmpty{
//                cell.lbl.backgroundColor = .yellow
//            }else{
//                cell.lbl.backgroundColor = .red
//            }
 
        }

 
        return cell
    }
 
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let w = tableView.frame.width
 
        let v = UIView.init(frame: CGRect.init(x: 0, y: 0, width: w, height: 50))
        v.backgroundColor = Theme.Color.White
 
        let lbl = UILabel.init(frame: CGRect.init(x: 8, y: 8, width: w - 16, height: 34))
        lbl.textColor = Theme.Color.red
        lbl.text = self.cats[section].category_name
        v.backgroundColor = .green
 
        v.addSubview(lbl)
 
        return v
    }
 
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return (isBrowse) ? 0 : 50
    }
 
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
 
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
 
        self.dismiss(animated: true) {
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "AdsVC") as! AdsVC
            self.parentVC?.navigationController?.pushViewController(vc, animated: true)
        }
    }
 
}

class MyGestuer : UITapGestureRecognizer{
    var tag : Int = 0
}
*/
